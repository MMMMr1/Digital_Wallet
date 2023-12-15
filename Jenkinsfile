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
                            accountServiceImage = docker.build("marymary88/account-service:1.0", "./account-service").push()
                            moneyTransferServiceImage = docker.build("marymary88/money-transfer-service:1.0", "./money-transfer-service").push()
                            apiGatewayServiceImage = docker.build("marymary88/api-gateway-service:1.0", "./api-gateway-service").push()
                            configurationServiceImage = docker.build("marymary88/configuration-service:1.0", "./configuration-service").push()
                            discoveryServiceImage = docker.build("marymary88/discovery-service:1.0", "./discovery-service").push()
                   }
                }
            }
        }
    }
}
