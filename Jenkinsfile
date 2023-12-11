pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                git branch: 'TASK8_add_tests',
                url: 'git@github.com:MMMMr1/digital_wallet.git'
            }
        }

        stage('Build') {
            steps {
                sh 'chmod +x ./gradlew'
                sh './gradlew clean build'
            }
        }
    }

    post {
        success {
            echo 'Build successful! Deploying...'
        }
        failure {
            echo 'Build failed! Notify someone...'
        }
    }
}