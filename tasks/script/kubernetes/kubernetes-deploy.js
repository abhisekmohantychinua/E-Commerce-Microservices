const {execSync} = require('child_process')


let args = process.argv
if (args.length === 2) {
    try {
        execSync(`../../../deploy/backend/config-server/deployment.yaml`, {stdio: "inherit"})
        execSync(`../../../deploy/backend/service-registry/deployment.yaml`, {stdio: "inherit"})
        execSync(`../../../deploy/backend/authorization-server/deployment.yaml`, {stdio: "inherit"})
        // execSync(`../../../deploy/backend/user-service/deployment.yaml`, {stdio: "inherit"})
        execSync(`../../../deploy/backend/api-gateway/deployment.yaml`, {stdio: "inherit"})
    } catch (err) {
        console.log('Error deploying services')
        console.log(err)
    }
}