# Routes

* [Backend](#backend)

## Backend

* [User](#user)
* [Product](#product)
* [Order and Cart](#order-and-cart)
* [Review](#review)
* [Payment](#payment)
* [Authentication](#authentication)

### User

> prefix: `../users/..`

|        Path        | Method | Role |                             Description                             |
|:------------------:|:------:|:----:|:-------------------------------------------------------------------:|
|      ../auth       |  GET   | ANY  | used by `Authorization Server` for authentication and authorization |
|         ..         |  POST  | ANY  |                       creating a user account                       |
|     ../profile     |  GET   | ANY  |                         getting own profile                         |
|    ../password     |  PUT   | ANY  |                        changing the password                        |
|     ../address     |  POST  | ANY  |                      create or add new address                      |
|     ../address     |  GET   | ANY  |                        get all user address                         |
| ../address/ < id > |  GET   | ANY  |                    get specific address of user                     |
| ../address/ < id > | DELETE | ANY  |                        to delete an address                         |

### Product

> prefix: `../products/..`

|         Path         | Method | Role  |                       Description                        |
|:--------------------:|:------:|:-----:|:--------------------------------------------------------:|
|          ..          |  POST  | ADMIN |                     to add a product                     |
| ../search/< pageNo > |  GET   |  ANY  | to get a product page (query parameters are in the code) |
|      ../< id >       |  GET   |  ANY  |                 to get specific product                  |
|  ../< id >/pictures  |  GET   |  ANY  |           to get pictures of specific product            |
|      ../< id >       |  PUT   | ADMIN |                to edit a specific product                |
|  ../< id >/pictures  |  PUT   | ADMIN |          to add pictures to a specific product           |
|   ../< id >/hidden   |  PUT   | ADMIN |              toggle visibility of a product              |
|      ../< id >       | DELETE | ADMIN |               to toggle product visibility               |
|  ../< id >/pictures  | DELETE | ADMIN |                 delete specific product                  |

### Order and Cart

> prefix: `../onc/..`

|       Path        | Method | Role |            Description            |
|:-----------------:|:------:|:----:|:---------------------------------:|
|        ..         |  POST  | ANY  |         to create a order         |
|     ../< id >     | PATCH  | ANY  |          to edit a order          |
| ../< id >/payment |  GET   | ANY  | to get payment status of an order |
|      ../cart      |  POST  | ANY  |        add product to cart        |
|      ../cart      | PATCH  | ANY  |      to edit a product cart       |
|  ../cart/< id >   | DELETE | ANY  |   to remove product from a cart   |
|      ../cart      | DELETE | ANY  |        to remove all carts        |
| ../deliver/< id > |  POST  | ANY  |     make a product delivered      |

### Review

> prefix: `../reviews/..`

|           Path           | Method | Role |            Description            |
|:------------------------:|:------:|:----:|:---------------------------------:|
|            ..            |  POST  | ANY  |   to add a review to a product    |
| ../products/< productId> |  GET   | ANY  | to get review on specific product |
|            ..            |  GET   | ANY  |       get users all review        |
|        ../< id >         |  GET   | ANY  |     to get a specific review      |
|        ../< id >         | PATCH  | ANY  |         to edit a review          |
|        ../< id >         | DELETE | ANY  |     to delete specific review     |

### Payment

> prefix: `../payment/..`

|      Path       |  Method  | Role |        Description        |
|:---------------:|:--------:|:----:|:-------------------------:|
|       ..        |   GET    | ANY  | to get all payment status |
| ../< order_id > | GET/POST | ANY  |    orders payment page    |

### Authentication

> prefix: `../auth/..`

| Path | Method | Role |            Description             |
|:----:|:------:|:----:|:----------------------------------:|
|  ..  |  POST  | NONE |        to create an account        |
|  --  |   --   |  --  | authentication specific end points |




    
