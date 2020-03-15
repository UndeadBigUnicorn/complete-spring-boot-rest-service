# It's a basic Rest application using Spring Boot with Spring Security, Spring Session, Spring JDBC templates, Validation, Exception Handling and Interceptors

## To run the application you should have MySQL running. You could find a database init script in `resources/static/database/init.sql`. You can also change database username and password in `application.properties`

## You could change server port in `application.properties` if `8080` is already in use

### To run the application execute a command `./gradlew bootRun`

### To build the application execute a command `./gradlew build` and than `java -jar build/libs/fullrestcomplete-0.0.1-SNAPSHOT.jar --spring.profiles.active=${environment}` 
### `environment` variable could be only `dev` for now because I've created only `application-dev.propertires` file