# JAVA_SPRING_WS_SECURITY_JWT
Demo de Spring Web con Spring Security usando JWT para mostrar en la presentacion de Seguridad e Integridad de la Informacion

La app por default levanta en el puerto 8080


### Metodos WS


usuarios y contraseñas [aqui](https://github.com/Fradantim/JAVA_SPRING_WS_SECURITY_JWT/blob/master/java-spring-security-jwt/src/main/java/com/seidli/javaspringsecurityjwt/persistence/UserService.java)
#### Autenticacion
	Endpoint: /authenticate
	TIPO: POST
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
		TIPO: GET
		Request Header:
			Content-Type: application/json
			Authorization: Bearer ${jwt}
		Response Body: String
