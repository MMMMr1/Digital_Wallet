pipeline {
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
    }
}