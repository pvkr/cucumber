# Cucumber example

Example for BDD REST API tests based on cucumber-jvm. <br/>

#### Build application
```bash
mvn clean package
```

#### Build docker image locally
```bash
mvn package -DskipTests=true dockerfile:build
```

#### Run application locally (required MySQL 5.7, schema=note_app, user=note_user, pswd=note_user)
* Intellij IDEA: run NoteApplication.java
* Console: ```mvn spring:boot:run```
* Console: ```java -jar target/cucumber-1.0.0.jar```

#### Run application locally on docker
```bash
docker network create note-app-network
docker run -d --name notes-mysql --network=note-app-network -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=note_app -e MYSQL_USER=note_user -e MYSQL_PASSWORD=note_user mysql:5.7
docker run -d --name notes-app   --network=note-app-network -p8080:8080 cucumber:latest
```

#### Run BDD tests on docker
```bash
mvn verify -Pbdd
```

#### Stop/Remove container
```bash
docker ps -f name=notes-mysql -q | xargs --no-run-if-empty docker container stop
docker ps -a -f name=notes-mysql -q | xargs docker container rm
```
