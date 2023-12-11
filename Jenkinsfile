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
                 sh './gradlew clean build'
            }
        }
    }
   tools {
     jdk 'JDK_17_new'
   }
}