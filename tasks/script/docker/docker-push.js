const {execSync} = require('child_process')
const data = require('./docker-build.json')


let args = process.argv
let pushImage = obj => {
    console.log(`Pushing ${obj.name}`)
    try {
        let cmd = `docker push coderabhisek/${obj.tag}:${obj.version}`
        console.log(`CMD: ${cmd}`)
        execSync(cmd, {stdio: "inherit"})
    } catch (err) {
        console.log(`Error pushing ${obj.name}`)
        console.log(err)
    }
    console.log(`Removing ${obj.name}`)
    try {
        let cmd = `docker image rm coderabhisek/${obj.tag}:${obj.version}`
        console.log(`CMD: ${cmd}`)
        execSync(cmd, {stdio: "inherit"})
    } catch (err) {
        console.log(`Error removing ${obj.name}`)
        console.log(err)
    }

}

if (args.length === 2) {
    data.forEach(pushImage)
} else if (args.length === 3) {
    data
        .filter(obj => obj.name === args[2])
        .forEach(pushImage)
} else {
    console.log('Invalid arguments')
}


