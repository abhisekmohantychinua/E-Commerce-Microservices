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

