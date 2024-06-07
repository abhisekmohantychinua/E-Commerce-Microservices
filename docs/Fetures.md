# Features

* [User](#user)
* [Product](#product)
* [Order and Cart](#order-and-carts)
* [Payment](#payment)
* [Authentication](#authentication)

## User

* User have unique id along with email and password for authentication.
* User can add products to carts and buy products instantly.
* The order and delivery will manage by `Order Service`.
* The product and cart functionality will be managed by `Product Service`.
* The user and client authentication will be provided by `Authorization Server` and all the requests that are coming.
* User review and customer service will be separately handled by `Customer Service`.
  through gateway will be authenticated according to the routes.
* There will be separate user service to interact with user and its functionalities.

## Product

* The product can't be deleted as it can be hidden by admin.
* All the

## Order and Carts

* When an order will be created a respective payment will be created which tracks the payment status.
* When the order will be delivered the payment status of the order will be verified. If the payment status is verified
  then only the order can be delivered.

> Payment status must be completed in order to deliver an order.

## Payment

* The payment page will handle the update of payment status.
* The server will serve the payment page and at the end redirect to the client.

## Authentication

* The user have to create account and the endpoint requires no authentication.
* Then user have to log in which will be handled by client and authorization server.