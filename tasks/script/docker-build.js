const {execSync} = require('child_process')
const data = require('./docker-build.json')


let args = process.argv
let buildImage = obj => {
    console.log(`Building ${obj.name}`)
    try {
        let cmd = `docker build -t coderabhisek/${obj.tag}:${obj.version} -f ${obj.dockerfile} ${obj.context}`
        console.log(`CMD: ${cmd}`)
        execSync(cmd, {stdio: "inherit"})
    } catch (err) {
        console.log(`Error building ${obj.name}`)
        console.log(err)
    }
}

if (args.length === 2) {
    data.forEach(buildImage)
} else if (args.length === 3) {
    data
        .filter(obj => obj.name = args[2])
        .forEach(buildImage)
} else {
    console.log('Invalid arguments')
}


