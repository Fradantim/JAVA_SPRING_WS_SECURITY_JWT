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
	Requiere estar autenticado
	Request Header:
		Content-Type: application/json
		Authorization: Bearer ${jwt}
	Response Body: String

---

### Ejemplos

#### Autenticacion
![1](https://raw.githubusercontent.com/Fradantim/JAVA_SPRING_WS_SECURITY_JWT/master/img/1.png)
![2](https://raw.githubusercontent.com/Fradantim/JAVA_SPRING_WS_SECURITY_JWT/master/img/2.png)



#### Saludo
![3](https://raw.githubusercontent.com/Fradantim/JAVA_SPRING_WS_SECURITY_JWT/master/img/3.png)



---

### Contenido del JWT [(jwt.io)](https://jwt.io/)
![4](https://raw.githubusercontent.com/Fradantim/JAVA_SPRING_WS_SECURITY_JWT/master/img/4.png)
