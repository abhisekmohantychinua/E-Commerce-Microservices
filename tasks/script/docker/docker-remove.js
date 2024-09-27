const {execSync} = require('child_process')
const data = require('./docker-build.json')


let args = process.argv
let removeImage = obj => {
    console.log(`Removing ${obj.name}`)
    try {
        let cmd = `docker image rm coderabhisek/${obj.tag}:${obj.version}`
        console.log(`CMD: ${cmd}`)
        execSync(cmd, {stdio: "inherit"})
    } catch (err) {
        console.log(`Error error ${obj.name}`)
        console.log(err)
    }
}

if (args.length === 2) {
    data.forEach(removeImage)
} else if (args.length === 3) {
    data
        .filter(obj => obj.name === args[2])
        .forEach(removeImage)
} else {
    console.log('Invalid arguments')
}
