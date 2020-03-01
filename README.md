# JAVA_SPRING_WS_SECURITY_JWT
Simple proof of concept of an JWT authentication server made with Spring Boot And Spring Security, originally made for the course "Seguridad e Integridad de la Informacion"

The app start on port 8080 by default

The simple-html-js-client is, as the name implies, a simple client, but also a little ugly and may not have the best practices on how to create a sessionless web.


### WebService Methods


Users and passwords [here](https://github.com/Fradantim/JAVA_SPRING_WS_SECURITY_JWT/blob/master/java-spring-security-jwt/src/main/java/com/seidli/javaspringsecurityjwt/persistence/UserService.java)
#### Autenticacion
	Endpoint: /authenticate
	Type: POST
	Request Body:
		{
			"username": String,
			"password": String
		}
	Response Body:
		{
			"jwt": String
		}

#### Saludo
	Endpoint: /hello
	Type: GET
	Requires being authenticated
	Request Header:
		Content-Type: application/json
		Authorization: Bearer ${jwt}
	Response Body: 
		{
			"response": String
		}
---

### Examples

#### Authentication
![1](https://raw.githubusercontent.com/Fradantim/JAVA_SPRING_WS_SECURITY_JWT/master/img/1.png)
![2](https://raw.githubusercontent.com/Fradantim/JAVA_SPRING_WS_SECURITY_JWT/master/img/2.png)



#### Greeting
![3](https://raw.githubusercontent.com/Fradantim/JAVA_SPRING_WS_SECURITY_JWT/master/img/3.png)



---

### JWT's content [(jwt.io)](https://jwt.io/)
![5](https://raw.githubusercontent.com/Fradantim/JAVA_SPRING_WS_SECURITY_JWT/master/img/5.png)
