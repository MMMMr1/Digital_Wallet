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
            stage('Build docker image'){
                steps{
                    script{
                        sh 'docker build -t user-service .'
                        sh 'docker build -t account-service .'
                    }
                }
            }
            stage('Run Docker Image') {
                steps {
                    sh 'gradle dockerRun'
                }
            }
        }
}