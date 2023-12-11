pipeline {
    agent any
    tools {
        gradle 'default'
    }
    stages {
        stage('Build') {
            steps {
                 sh 'gradle clean build'
            }
        }
    }
}