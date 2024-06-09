# Authentication

## How it looks from user side?

* The application follows a very complex authentication and authorization flow.
* First a user has to register/signup him by an open endpoint.
* Then he has to log in through authorization server to get an `access_token` by following the PKCE flow.
* After user has the access_token user can access the secured resources.

## How the whole process is working in the backend?

The whole authentication is divided into 2 parts:

* [Authentication(granting a token)](#authentication-1)
* [Authorization(validating the token)](#authorization)

### Authentication

![authentication_flow](../resources/authorization_flow.svg)

### Authorization

![authorization_flow](../resources/token_validation.svg)