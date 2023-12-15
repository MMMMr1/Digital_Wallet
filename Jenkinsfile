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
                userServiceImage = docker.build("marymary88/user-service:$BUILD_NUMBER", "./user-service")
                accountServiceImage = docker.build("marymary88/account-service:$BUILD_NUMBER", "./account-service")
                moneyTransferServiceImage = docker.build("marymary88/money-transfer-service:$BUILD_NUMBER", "./money-transfer-service")
                apiGatewayServiceImage = docker.build("marymary88/api-gateway-service:$BUILD_NUMBER", "./api-gateway-service")
                configurationServiceImage = docker.build("marymary88/configuration-service:$BUILD_NUMBER", "./configuration-service")
                discoveryServiceImage = docker.build("marymary88/discovery-service:$BUILD_NUMBER", "./discovery-service")
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
                sh "docker rmi $userServiceImage:$BUILD_NUMBER"
                sh "docker rmi $accountServiceImage:$BUILD_NUMBER"
                sh "docker rmi $moneyTransferServiceImage:$BUILD_NUMBER"
                sh "docker rmi $apiGatewayServiceImage:$BUILD_NUMBER"
                sh "docker rmi $configurationServiceImage:$BUILD_NUMBER"
                sh "docker rmi $discoveryServiceImage:$BUILD_NUMBER"
            }
        }
    }
}
