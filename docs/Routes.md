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

|           Path            | Method | Role |         Description          |
|:-------------------------:|:------:|:----:|:----------------------------:|
|        ../profile         |  GET   | ANY  |   to get the user profile    |
|        ../password        |  POST  | ANY  |      to change password      |
|         ../orders         |  GET   | ANY  |      to get all orders       |
|    ../orders/<  id  >     |  GET   | ANY  |    to get specific order     |
|         ../carts          |  GET   | ANY  |       to get all carts       |
| ../carts/<  product_id  > |  GET   | ANY  | to get specific product cart |
|       ../addresses        |  GET   | ANY  |     to get all addresses     |
|   ../addresses/<  id  >   |  GET   | ANY  |   to get specific address    |
|       ../addresses        |  POST  | ANY  |      to add new address      |
|   ../addresses/<  id  >   | PATCH  | ANY  |   to edit specific address   |
|    ../addresses/< id >    | DELETE | ANY  |  to delete specific address  |
|        ../payments        |  GET   | ANY  |  to get all saved payments   |
|        ../reviews         |  GET   | ANY  |   to get all given reviews   |

### Product

> prefix: `../products/..`

|       Path        | Method | Role  |                       Description                        |
|:-----------------:|:------:|:-----:|:--------------------------------------------------------:|
|        ..         |  POST  | ADMIN |                     to add a product                     |
|        ..         |  GET   |  ANY  | to add a product page (query parameters are in the code) |
|     ../< id >     |  GET   |  ANY  |                 to get specific product                  |
|     ../< id >     | PATCH  | ADMIN |                to edit a specific product                |
|     ../< id >     | DELETE | ADMIN |               to toggle product visibility               |
| ../< id >/reviews |  GET   |  ANY  |          to get all the reviews of the product           |

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

> prefix: `../review/..`

|       Path        | Method | Role |         Description          |
|:-----------------:|:------:|:----:|:----------------------------:|
| ../< product_id > |  POST  | ANY  | to add a review to a product |
|     ../< id >     |  GET   | ANY  |   to get a specific review   |
|     ../< id >     | PATCH  | ANY  |       to edit a review       |
|     ../< id >     | DELETE | ANY  |  to delete specific review   |

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




    
