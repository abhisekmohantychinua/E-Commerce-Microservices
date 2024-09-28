# Project Documentation: `tasks`

## Overview

This project, named `tasks`, is designed to maintain and automate various build and deployment tasks using npm scripts.
The key focus is to streamline Docker and Kubernetes operations through a set of pre-defined commands.

## Basic Information

- **Project Name:** `tasks`
- **Version:** `1.0.0`
- **Description:** This project is for maintaining commands as scripts/tasks to automate build and deployment processes.
- **Author:** Abhisek Mohanty
- **License:** UNLICENSED

## Scripts

The following scripts are defined under the `"scripts"` section in the `package.json` file:

### Docker Scripts

1. **`docker-deploy`**
    - **Command:** `docker-compose -f ../docker-compose.yml up -d`
    - **Description:** Starts Docker containers using the `docker-compose.yml` file located one directory up.

2. **`stop-docker-deploy`**
    - **Command:** `docker-compose -f ../docker-compose.yml down`
    - **Description:** Stops and removes Docker containers started with the `docker-deploy` command.

3. **`docker-database`**
    - **Command:** `docker-compose -f ../docker/docker-compose-dev.yml up -d`
    - **Description:** Starts Docker containers for the database using the `docker-compose-dev.yml` file in the `docker`
      directory.

4. **`stop-docker-database`**
    - **Command:** `docker-compose -f ../docker/docker-compose-dev.yml down`
    - **Description:** Stops and removes database containers started with the `docker-database` command.

5. **`docker-build`**
    - **Command:** `node script/docker/docker-build.js`
    - **Description:** Executes the Docker build process using the custom script `docker-build.js`.

6. **`docker-remove`**
    - **Command:** `node script/docker/docker-remove.js`
    - **Description:** Executes a script to remove Docker images/containers as defined in `docker-remove.js`.

7. **`docker-push`**
    - **Command:** `node script/docker/docker-push.js`
    - **Description:** Pushes the Docker images to a repository using the `docker-push.js` script.

### Kubernetes Scripts

1. **`kub-active`**
    - **Command:
      ** `kubectl apply -f ../deploy/deployment-namespace.yaml && kubectl config set-context --current --namespace=e-commerce`
    - **Description:** Activates the Kubernetes namespace `e-commerce` by applying the namespace configuration and
      setting the current context to this namespace.

2. **`kub-database`**
    - **Command:** `node script/kubernetes/kubernetes-database.js`
    - **Description:** Deploys database configurations in Kubernetes using the `kubernetes-database.js` script.

3. **`kub-deploy`**
    - **Command:** `npm run kub-active && npm run kub-database && node script/kubernetes/kubernetes-deploy.js`
    - **Description:** Executes a series of tasks:
        1. Activates the `e-commerce` namespace.
        2. Deploys database configurations.
        3. Executes the Kubernetes deployment script `kubernetes-deploy.js`.

4. **`kub-deactivate`**
    - **Command:** `kubectl delete namespace e-commerce`
    - **Description:** Deactivates and removes the `e-commerce` namespace from Kubernetes.

### Other Scripts

1. **`test`**
    - **Command:** `echo Hello World`
    - **Description:** Prints "Hello World" to the console, used for testing.

## Dependencies

The project has a single dependency:

- **`dockerode`** (`^4.0.2`): A Node.js Docker client library to interact with Docker programmatically.

## Usage Guide

1. **Starting Docker Deployment:**
    - Use `npm run docker-deploy` to start Docker containers for the main deployment.
    - Use `npm run stop-docker-deploy` to stop these containers.

2. **Managing Databases in Docker:**
    - Use `npm run docker-database` to start database containers.
    - Use `npm run stop-docker-database` to stop these containers.

3. **Building and Pushing Docker Images:**
    - Use `npm run docker-build` to build Docker images.
    - Use `npm run docker-push` to push the images to a repository.

4. **Deploying to Kubernetes:**
    - Use `npm run kub-deploy` to activate the namespace, deploy the database, and execute the deployment script.
    - Use `npm run kub-deactivate` to delete the `e-commerce` namespace.

5. **Testing:**
    - Use `npm run test` to run a basic test script that outputs "Hello World".

## Conclusion

This documentation provides a high-level overview of the project configuration and usage instructions. Each script
automates a specific aspect of deployment and management for both Docker and Kubernetes, making the overall process more
efficient and repeatable.
