pipeline {
    agent any 
    tools { 
        maven 'Maven 3.3.9'
        jdk 'JDK-8.172'
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
    }
    stages {
        stage ('Build') {
            steps {
                sh 'mvn -f pom.xml clean package'
            }
            post {
                always {
                    junit 'target/surefire-reports/**/*.xml'
                }
            }
        }
        stage('Sonar') {
            steps {
                withSonarQubeEnv('Sonar') {
                  sh 'mvn -f pom.xml org.sonarsource.scanner.maven:sonar-maven-plugin:3.3.0.603:sonar' +
                  ' -f pom.xml' +
                  ' -Dsonar.projectKey=com.github.pvkr.cucumber:cucumber' +
                  ' -Dsonar.language=java' +
                  ' -Dsonar.sources=src/main/java' +
                  ' -Dsonar.tests=src/test/java' +
                  ' -Dsonar.exclusions=**/NoteApplication.java' +
                  ' -Dsonar.jacoco.reportPaths=target/coverage-reports/jacoco.exec'
                }
            }
        }
        stage('Deploy') {
            steps {
                sh '''
                     if [ -z `docker network ls -f name=note-app-network -q` ]; then
                        echo "Note application network bridge isn't existed, creating ..."
                        docker network create note-app-network
                     fi
                   '''

                sh '''
                     if [ -z `docker ps -f name=notes-mysql -q` ]; then
                        echo "Note application Mysql DB isn't existed, creating ..."
                        docker run -d --name notes-mysql --network=note-app-network -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=note_app -e MYSQL_USER=note_user -e MYSQL_PASSWORD=note_user mysql:5.7
                     fi
                   '''

                echo "Updating Note application image ..."
                sh 'docker ps -f ancestor=cucumber:latest -q | xargs --no-run-if-empty docker container stop'
                sh 'docker container ls -a -f ancestor=cucumber:latest -q | xargs -r docker container rm'
                sh 'mvn -f pom.xml package -DskipTests=true dockerfile:build'

                echo "Running Note application ..."
                sh 'docker run -d --name notes-app --network=note-app-network -p8004:8080 cucumber:latest'
            }
        }
        stage('Acceptance-Tests') {
            steps {
                sh 'mvn -f pom.xml verify -Pbdd -Dnote.url=http://localhost:8004/api'
            }
            post {
                always {
                    cucumber fileIncludePattern: 'target/cucumber.json'
                }
            }
        }
    }
}