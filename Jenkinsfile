pipeline {
    agent any
    tools {
        gradle '8.5'
    }
    stages {
            stage('Build') {
                steps {
                    sh 'gradle assemble'
                }
            }
             stage('Test') {
                steps {
                    sh 'gradle test'
                }
            }
            stage('Build Docker Image') {
                steps {
                    docker.build("user_service:1.0.0", "./user_service")
                }
            }
            stage('Run Docker Image') {
                steps {
                    sh 'gradle dockerRun'
                }
            }
        }
}