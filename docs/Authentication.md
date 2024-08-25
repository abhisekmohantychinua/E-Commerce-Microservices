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

# Authorization Code + PKCE Security Flow and Rules

## Overview

PKCE (Proof Key for Code Exchange) is an extension to the OAuth 2.0 Authorization Code flow to prevent authorization
code interception attacks. It is primarily designed for public clients (e.g., native or single-page applications) that
cannot securely store a client secret.

## Flow

1. **Client Generates Code Verifier and Code Challenge:**
    - The client creates a random string called a **code verifier**.
    - The code verifier is then hashed (using SHA-256, usually) to create a **code challenge**.

2. **Authorization Request:**
    - The client sends the user to the authorization server's authorization endpoint.
    - The client includes the following parameters in the request:
        - `response_type=code`
        - `client_id`
        - `redirect_uri`
        - `scope` (optional but recommended)
        - `state` (recommended)
        - `code_challenge` (the hashed code verifier)
        - `code_challenge_method=S256` (specifies the method used to hash the code verifier)

3. **User Authentication and Authorization:**
    - The user authenticates and authorizes the client.
    - The authorization server redirects the user back to the client's `redirect_uri` with an authorization code.

4. **Token Request:**
    - The client exchanges the authorization code for an access token by sending a POST request to the authorization
      server's token endpoint.
    - The client includes the following parameters in the request:
        - `grant_type=authorization_code`
        - `code` (the authorization code received)
        - `redirect_uri`
        - `client_id`
        - `code_verifier` (the original code verifier, not the hashed one)

5. **Token Response:**
    - The authorization server verifies the code verifier against the code challenge.
    - If valid, the server issues an access token (and optionally a refresh token) to the client.

## Rules

- **Code Verifier:** A high-entropy cryptographic random string between 43 and 128 characters.
- **Code Challenge:** A derived value from the code verifier (either plain or hashed using SHA-256).
- **Code Challenge Method:** Typically set to `S256` (SHA-256) for better security.

---

# Authorization Code Interception Attacks

## Explanation

An authorization code interception attack occurs when an attacker intercepts the authorization code during the OAuth 2.0
Authorization Code flow. The attacker can then use the stolen code to exchange it for an access token, thus gaining
unauthorized access to the resources.

### Prevention:

- **PKCE:** By using PKCE, even if the authorization code is intercepted, the attacker cannot exchange it for an access
  token without the original code verifier, which only the legitimate client possesses.
- **State Parameter:** The `state` parameter is used to prevent CSRF attacks by including a unique, unpredictable value
  in the request, which is checked upon return.

---

# Is `scope` Mandatory in the Authorization Code Request?

No, the `scope` parameter is not mandatory in the authorization code request, but it is highly recommended. The `scope`
defines the permissions that the client is requesting, and it allows the authorization server to determine what access
should be granted.

---

# Explanation of Request Parameters in Authorization Code Flow

- **`response_type=code`:** Specifies that the client is requesting an authorization code.
- **`client_id`:** The unique identifier of the client making the request.
- **`redirect_uri`:** The URL to which the authorization server will redirect the user after granting authorization.
  Must match one of the URLs registered with the authorization server.
- **`scope`:** (Optional) A space-delimited list of permissions that the client is requesting. Helps the authorization
  server determine what level of access the client is asking for.
- **`state`:** (Recommended) A unique string that the client generates to maintain state between the request and the
  callback. Used to protect against CSRF attacks.
- **`code_challenge`:** A challenge derived from the code verifier, sent to the authorization server to enable PKCE.
- **`code_challenge_method`:** Specifies the method used to generate the code challenge (e.g., `S256` for SHA-256
  hashing).

---

# Why to make `pkce` mandatory?

An SPA cannot securely store credentials and therefore must be treated as a public client. Public clients should be
required to use Proof Key for Code Exchange (PKCE). PKCE is important to prevent
the [PKCE Downgrade Attack](https://datatracker.ietf.org/doc/html/draft-ietf-oauth-security-topics-25#name-pkce-downgrade-attack).


