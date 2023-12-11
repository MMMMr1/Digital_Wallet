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
    }
    stages {
        stage('Test') {
            steps {
                 sh 'gradle :user-service:test'
            }
        }
    }
    stages {
        stage('Build') {
            steps {
                 sh 'gradle build'
            }
        }
    }
}