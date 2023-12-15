pipeline {
    environment {
        registryCredential = 'docker_cred'
        userServiceImage = ''
        accountServiceImage = ''
        moneyTransferServiceImage = ''
        apiGatewayServiceImage = ''
        configurationServiceImage = ''
        discoveryServiceImage = ''
    }
    agent any
    tools {
        gradle '8.5'
    }

    stages {
        stage('Build') {
            steps {
                sh 'gradle clean build'
            }
        }
        stage('Building images') {
            steps {
                script {
                userServiceImage = docker.build("marymary88/user-service:1.0", "./user-service")
                accountServiceImage = docker.build("marymary88/account-service:1.0", "./account-service")
                moneyTransferServiceImage = docker.build("marymary88/money-transfer-service:1.0", "./money-transfer-service")
                apiGatewayServiceImage = docker.build("marymary88/api-gateway-service:1.0", "./api-gateway-service")
                configurationServiceImage = docker.build("marymary88/configuration-service:1.0", "./configuration-service")
                discoveryServiceImage = docker.build("marymary88/discovery-service:1.0", "./discovery-service")
                }
            }
        }
        stage('Deploy images') {
            steps {
                script {
                   docker.withRegistry('', registryCredential) {
                        userServiceImage.push()
                        accountServiceImage.push()
                        moneyTransferServiceImage.push()
                        apiGatewayServiceImage.push()
                        configurationServiceImage.push()
                        discoveryServiceImage.push()
                    }
                }
            }
        }
        stage('Cleaning up') {
            steps {
                sh "docker rmi $userServiceImage:1.0"
                sh "docker rmi $accountServiceImage:1.0"
                sh "docker rmi $moneyTransferServiceImage:1.0"
                sh "docker rmi $apiGatewayServiceImage:1.0"
                sh "docker rmi $configurationServiceImage:1.0"
                sh "docker rmi $discoveryServiceImage:1.0"
            }
        }
    }
}
