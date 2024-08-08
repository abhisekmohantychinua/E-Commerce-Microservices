# Errors

## Error Code

<div style="text-align: center;width: 100%">
    <u style="font-size: xxx-large">XYZ</u>
</div>

* Here `X` represents the service code, i.e. which service caused the error.
* `Y` represents the sub-service code, i.e. which section under the service caused the error.
* `Z` represents the actual-exception code, i.e. which exception is happened under the server.
* Sub-service code `0` represent global for the particular service.

## User Service (1YZ)

All the error from the service begins with `1`, sub-service code for `user` is `1` and `address` is `2`.

### INVALID_ARGUMENT

- **Code**: 100
- **Status**: 400 Bad Request
- **Description**: This error occurs when invalid attributes/arguments are provided.

### USER_NOT_FOUND

- **Code**: 114
- **Status**: 404 Not Found
- **Description**: This error occurs when the requested user is not found on the server with provided credentials.

### ADDRESS_NOT_FOUND

- **Code**: 124
- **Status**: 404 Not Found
- **Description**: This error occurs when the requested address is not found on the server with provided credentials.

### IMAGE_UNSUPPORTED

- **Code**: 105
- **Status**: 415 Unsupported Media Type
- **Description**:
    - This error occurs when the provided image has an unsupported extension.
    - This error occurs when the filename is invalid/illegal.

### SERVER_ERROR

- **Code**: 106
- **Status**: 500 Internal Server Error
- **Description**:
    - This error occurs when something unexpected happens in the server.
    - This error occurs when there is some IO error during file handling.

## Product Service (2YZ)

All the error from the service begins with `2` and there is no sub service so sub-service code is `0`.

### INVALID_ARGUMENT

- **Code**: 200
- **Status**: 400 Bad Request
- **Description**:
    - This error occurs when invalid attributes/arguments provided.

### PRODUCT_NOT_FOUND

- **Code**: 204
- **Status**: 404 Not Found
- **Description**:
    - This error occurs when requested product is not found on server.

### IMAGE_UNSUPPORTED

- **Code**: 205
- **Status**: 415 Unsupported Media Type
- **Description**:
    - This error occurs when the provided image has an unsupported extension.
    - This error occurs when the filename is invalid/illegal.

### SERVER_ERROR

- **Code**: 206
- **Status**: 500 Internal Server Error
- **Description**:
    - This error occurs when there is something unexpected occur in the server.
    - This error occurs when there is some IO error occurs during file handling.

## Review Service (5YZ)

All the error from the service begins with `5` and there is not sub-service, but it uses some of the exceptions from
parent services.

### INVALID_ARGUMENT

- **Code**: 500
- **Status**: 400 Bad Request
- **Description**:
    - This error occurs when invalid attributes/arguments provided.

### USER_NOT_FOUND

- **Code**: 114
- **Status**: 404 Not Found
- **Description**:
    - This error occurs when requested user is not found on server with provided credentials.

### PRODUCT_NOT_FOUND

- **Code**: 204
- **Status**: 404 Not Found
- **Description**:
    - This error occurs when requested product is not found on server.

### REVIEW_NOT_FOUND

- **Code**: 504
- **Status**: 404 Not Found
- **Description**:
    - This error occurs when requested review is not found on server.

### PERMISSION_DENIED

- **Code**: 502
- **Status**: 403 Forbidden
- **Description**:
    - This error occurs when you are trying to access/modify other users entity.

### SERVER_ERROR

- **Code**: 506
- **Status**: 500 Internal Server Error
- **Description**:
    - This error occurs when there is something unexpected occur in the server.
    - This error occurs when there is some IO error occurs during file handling.
