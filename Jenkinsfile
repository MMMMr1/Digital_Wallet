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
                 sh 'gradle :user-service:test'
            }
        }
        stage('Build') {
            steps {
                 sh 'gradle build'
            }
        }
    }
    System.setProperty("org.jenkinsci.plugins.durabletask.BourneShellScript.HEARTBEAT_CHECK_INTERVAL", "3800");

}