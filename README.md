# Workshop 2 (OAuth and Security Concerns in Rest APIs)

### What you need to have installed: 

- Java 8
- Apache Maven 3.3.9

### How to run the application

    mvn spring-boot:run

### Authentication

We are using OAuth2 and JWT to authenticate the user, the request example is in the Postman collection, the user to authenticate is the following 

    username: user
    password: password
    
The JWT token stores also the user information to be able to log each request. Currently the logging is being done to be stored in a file and to be printed in the terminal.

*Note: The file name where the logging is stored is `log.txt` at the project root level.*

### How to run the application tests

    mvn test
### Notes

If you want to test the application from Postman, you have to install Postman, here are the instructions: https://www.getpostman.com/docs/introduction

Using Postman you can run the postman collection that is in the file with this name: 
`Workshop 2.postman_collection.json`

This collection will authenticate and store the access token and use it to make the second call to the `greeting` resource.

If you don't have experience with Postman you can follow this tutorial to import Postman collections.
https://www.getpostman.com/docs/collections
