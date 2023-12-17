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
        stage('SonarQube Analysis'){
            steps {
                withSonarQubeEnv("sonarqube1") {
                    sh './gradlew sonar \
                          -Dsonar.projectKey=mary_wallet \
                          -Dsonar.host.url=http://82.97.240.173:9000 \
                          -Dsonar.login=sqp_b4fc4e9bc84463bf79b030788851628b0da3eb32'
                }
            }
        }
        stage('Quality Gate'){
            steps {
                timeout(time: 2, unit: 'MINUTES'){
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Building images') {
            steps {
                script {
                userServiceImage = docker.build("marymary88/user-service:1.0", "./user-service")
                moneyTransferServiceImage = docker.build("marymary88/money-transfer-service:1.0", "./money-transfer-service")
                apiGatewayServiceImage = docker.build("marymary88/api-gateway-server:1.0", "./api-gateway-server")
                configurationServiceImage = docker.build("marymary88/configuration-server:1.0", "./configuration-server")
                discoveryServiceImage = docker.build("marymary88/discovery-server:1.0", "./discovery-server")
                accountServiceImage = docker.build("marymary88/account-service:1.0", "./account-service")
                }
            }
        }
        stage('Deploy images') {
            steps {
                script {
                   docker.withRegistry('', registryCredential) {
                        userServiceImage.push()
                        moneyTransferServiceImage.push()
                        apiGatewayServiceImage.push()
                        configurationServiceImage.push()
                        discoveryServiceImage.push()
                        accountServiceImage.push()
                    }
                }
            }
        }
        stage('Cleaning up') {
            steps {
                sh "docker system prune -f"
            }
        }
    }
}
