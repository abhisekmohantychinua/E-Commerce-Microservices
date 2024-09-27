const {execSync} = require('child_process')
const fs = require('fs')
const path = require('path')

const persistentPath = '../../../deploy/backend/persistent'

fs.readdir(persistentPath, (err, files) => {
    if (err) {
        console.log('Error reading yaml files.')
    }
    if (files) {
        files.forEach((file) => {
            let filePath = path.join(persistentPath, file)
            let stat = fs.statSync(filePath)
            if (stat.isFile()) {
                try {
                    execSync(`kubectl apply -f ${filePath}`, {stdio: "inherit"})
                } catch (err) {
                    console.log('Error creating pv/pvc')
                    console.log(err)
                }
            }
        })
    }
})

try {
    execSync(`../../../deploy/backend/database/mongodb/deployment-secret.yaml`, {stdio: "inherit"})
    execSync(`../../../deploy/backend/database/mongodb/deployment.yaml`, {stdio: "inherit"})
    execSync(`../../../deploy/backend/database/mysql/deployment-secret.yaml`, {stdio: "inherit"})
    execSync(`../../../deploy/backend/database/mysql/deployment.yaml`, {stdio: "inherit"})
} catch (err) {
    console.log('Error deploying database')
    console.log(err)
}
