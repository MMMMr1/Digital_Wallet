pipeline {
    agent any
    tools {
        gradle '8.5'
    }
    stages {
        stage('Clean') {
            steps {
                sh 'gradle clean'
            }
        }
        stage('Test') {
            steps {
                sh 'gradle test'
            }
        }
        stage('Build') {
            steps {
                sh 'gradle build'
            }
        }
    stage('Build and push images') {
                steps {
                    script {
                        docker.withRegistry('', 'docker_cred') {
                            userServiceImage = docker.build("marymary88/user-service:1.0", "./user-service").push()
                  }
                    }
                }
            }
    }
}
