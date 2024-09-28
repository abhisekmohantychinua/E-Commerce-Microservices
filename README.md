<h1 style="width: 100vw;text-align: center; margin-bottom: 5rem;">E Commerce Microservice</h1>

<div style="display: flex; justify-content: space-around; gap: 2rem; flex-wrap: wrap;">
    <img src="/resources/java.png" alt="oauth2" height="125"/>
    <img src="/resources/js.png" alt="oauth2" height="125"/>
    <img src="/resources/spring.png" alt="oauth2" height="125"/>
    <img src="/resources/mysql.png" alt="oauth2" height="125"/>
    <img src="/resources/mongodb.png" alt="oauth2" height="125"/>
    <img src="/resources/ij.png" alt="oauth2" height="125"/>
    <img src="/resources/git.png" alt="oauth2" height="125"/>
    <img src="/resources/docker.png" alt="oauth2" height="125"/>
    <img src="/resources/k8s.png" alt="oauth2" height="125"/>
    <img src="/resources/oauth2.png" alt="oauth2" height="125"/>
    <img src="/resources/npm.png" alt="oauth2" height="125"/>
    <img src="/resources/figma.png" alt="oauth2" height="125"/>
</div>

# Navigation

* [Objective](#objective)
* [Project Overview](#project-overview)
* [Technology Stack](#technology-stack)
* [Key Features](#key-features)
* [Project Flow](#project-flow)
* [Docker Implementation](#docker-implementation)
* [Kubernetes Implementation](#kubernetes-implementation)
* [Kubernetes & Deployment](./deploy)
    * [Namespace](./deploy/deployment-namespace.yaml)
    * [Persistent Volume Mounts](./deploy/backend/persistent)
        * [MySQL](./deploy/backend/persistent/mysql-persistent.yaml)
        * [MongoDB](./deploy/backend/persistent/mongodb-persistent.yaml)
        * [Uploads](./deploy/backend/persistent/uploads-persistent.yaml)
    * [Databases](./deploy/backend/database)
        * [MySQL](./deploy/backend/database/mysql/deployment.yaml) & [Secrets](./deploy/backend/database/mysql/deployment-secret.yaml)
        * [MongoDB](./deploy/backend/database/mongodb/deployment.yaml) & [Secrets](./deploy/backend/database/mongodb/deployment-secret.yaml)
    * [Service Deployments](./deploy/backend)
        * [Config Server](./deploy/backend/config-server/deployment.yaml)
        * [Service Registry](./deploy/backend/service-registry/deployment.yaml)
        * [Authorization Server](./deploy/backend/authorization-server/deployment.yaml)
        * [Api Gateway](./deploy/backend/api-gateway/deployment.yaml)
        * [User Service](./deploy/backend/user-service/deployment.yaml)
        * [Product Service](./deploy/backend/product-service/deployment.yaml)
        * [Order Service](./deploy/backend/order-service/deployment.yaml)
        * [Payment Service](./deploy/backend/payment-service/deployment.yaml)
        * [Review Service](./deploy/backend/review-service/deployment.yaml)
* [Docker Compose (Deployment using Docker)](./docker-compose.yml)
* [Docker image & Containers](./docker)
    * [Config Server](./docker/backend/config-server/Dockerfile)
    * [Service Registry](./docker/backend/service-registry/Dockerfile)
    * [Authorization Server](./docker/backend/authorization-server/Dockerfile)
    * [Api Gateway](./docker/backend/api-gateway/Dockerfile)
    * [User Service](./docker/backend/user-service/Dockerfile)
    * [Product Service](./docker/backend/product-service/Dockerfile)
    * [Order Service](./docker/backend/order-service/Dockerfile)
    * [Payment Service](./docker/backend/payment-service/Dockerfile)
    * [Review Service](./docker/backend/review-service/Dockerfile)
    * [Database Docker Compose (for development)](./docker/docker-compose-dev.yml)
* [Docs](./docs)
    * [Authentication & Authorization](./docs/Authentication.md)
    * [Entities](./docs/Entity.md)
    * [Errors](./docs/Errors.md)
    * [Payment](./docs/Payment.md)
* [Resources](./resources)
    * [Raw Editable XML(.drawio) files](./resources/raw)
* [Services](./services)
    * [Config Server](./services/backend/config-server)
    * [Service Registry](./services/backend/service-registry)
    * [Authorization Server](./services/backend/authorization-server)
    * [Api Gateway](./services/backend/api-gateway)
    * [User Service](./services/backend/user-service)
    * [Product Service](./services/backend/product-service)
    * [Order Service](./services/backend/order-service)
    * [Payment Service](./services/backend/payment-service)
    * [Review Service](./services/backend/review-service)
* [Tasks & Build Automation](./tasks)

## Objective:

The primary goal of this project is to build a scalable, robust, and secure e-commerce platform using a microservice
architecture. This approach enables independent development, deployment, and scaling of services, improving the
platform's flexibility and resilience. The project focuses on integrating multiple services such as user management,
product catalog, payment, and authorization into a seamless, functional system.

## Project Overview:

The e-commerce platform is composed of several microservices, each responsible for a specific functionality. The
services are loosely coupled, and each microservice can be deployed independently, ensuring fault tolerance and ease of
maintenance. Key services include:

1. **User Service**: Manages user profiles, registration, and authentication.
2. **Product Service**: Manages product listings, categories, and inventory.
3. **Order Service**: Manages user orders and carts.
4. **Payment Service**: Manages payment transactions.
5. **Review Service**: Keeps User reviews.
6. **Authorization Server**: Provides OAuth2-based authorization for secure access control.
7. **API Gateway**: Acts as the entry point for external traffic and routes requests to appropriate services.
8. **Discovery Server (Eureka)**: Facilitates service discovery, enabling services to locate each other without manual
   configuration.
9. **Config Server**: Global configuration hub for all other services

## Technology Stack:

- **Backend**: Spring Boot, Spring Cloud (Eureka, API Gateway)
- **Frontend**: React/Angular for user interface (optional depending on project requirements)
- **Database**: MySQL, MongoDB
- **Authentication & Authorization**: Spring Security, OAuth2, Spring Authorization Server, Spring Resource Server
- **Containerization**: Docker for containers
- **Kubernetes**: For container orchestration and managing internal communication
- **Orchestration**: Eureka for service discovery
- **Build & Dependency Management**: Gradle
- **Scripting & Task Automation**: NPM, Javascript

## Key Features:

- **Microservices Architecture**: Each functionality is encapsulated within a dedicated microservice.
- **Service Discovery**: Eureka server ensures seamless communication between services without hardcoding IP addresses.
- **API Gateway**: Centralized gateway routes all requests, providing security and simplified service access.
- **OAuth2 Authentication**: Ensures secure access through an authorization server for different types of clients and
  resources.
- **Docker**: Each service runs in its container, managed by Docker Compose, facilitating portability and consistency.
- **Persistence**: MySQL and MongoDB database for data storage with bind-mounted local volumes to ensure data
  persistence across
  container restarts.

## Project Flow:

1. **User Journey**: Users can register and log in to the platform. Secure OAuth2 tokens authenticate each request.
2. **Service Discovery**: When a user accesses a service, the API Gateway routes the request to the relevant
   microservice (e.g., user-service, product-service), using Eureka to dynamically resolve service instances.
3. **Payment Integration**: The payment-service interacts with other services securely, ensuring transactions are
   processed smoothly.
4. **Data Storage**: All data (user profiles, products, orders) is stored in a MySQL database container, which is
   persistent via volume mounts.
5. **Security**: Sensitive operations, such as payment, are protected through session login and secured endpoints.

## Docker Implementation:

- **Docker Images**: Custom Docker images are built for each microservice.
- **Docker Compose**: A `docker-compose.yml` file orchestrates the services, ensuring that all containers (services) are
  correctly networked.
- **Named Volumes**: Ensures data persistence by linking MySQL database files to a local directory for long-term data
  storage.

## Kubernetes Implementation:

- **Docker Images**: Each image will be pulled from Docker HUB and serve as deployment.
- **Services**: Services for each deployment so that containers can communicate seamlessly.
- **Volume Mount**: MySQL, MongoDB and Uploads will have volume mounts for persistent data storage.

---

This project showcases a comprehensive understanding of microservices, cloud-native development, containerization, and
secure service-to-service communication, making it a valuable contribution to modern web development and a robust
solution for scalable e-commerce platforms.
