# Spring Security Example

This example fulfills the following requirement: two authentication strategies applied to the same set of resources. Grant access if the SSO credentials exist, otherwise default to Basic Auth.

The SSO mechanism is assumed to be part of a managed platform, whereby an SSO user is authenticated based on the presence of an "x-auth-user" header.

If no SSO header is present, HTTP Basic Auth will be used. 

One demo basic auth user is configured: `basicUser:basicPassword`

## Run the demo

Run `SpringSecurityExampleApplication.main` 

or from the terminal `./gradlew bootRun`

Try out some requests from the terminal:
```
# an unsecured endpoint should respond successfully
curl http://localhost:8080/unprotected

# passing no credentials to a secured endpoint should return a 401
curl http://localhost:8080/protected

# passing the SSO header should respond successfully
curl http://localhost:8080/protected -H "x-auth-user: ssoUserName"

# passing valid basic auth credentials should respond successfully
curl http://localhost:8080/protected -u basicUser:basicPassword

# passing valid both basic auth and SSO credentials should use SSO to authenticate
curl http://localhost:8080/protected  -u basicUser:basicPassword -H "x-auth-user: ssoUser"
```