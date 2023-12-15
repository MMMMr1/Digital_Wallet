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
        stage('Tesr') {
            steps {
                sh 'gradle test'
                junit '**/test-results/test/*.xml'
            }
        }
        stage('Build') {
            steps {
                sh 'gradle build'
            }
        }
    }
}
