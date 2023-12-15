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
    }
}
