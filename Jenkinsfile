pipeline {
    agent any
    environment {
        PATH = "C:\\Program Files\\apache-maven-3.9.8\\bin;${env.PATH}"
    }
    stages {
        stage('Get Code') {
            steps {
                git credentialsId: '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', url: 'https://github.com/mayurb6706/Jenkins-Rest-Api.git'
            }
        }
        stage('Build') {
            steps {
                // Use 'bat' to run the Maven command in Windows
                bat 'mvn clean package'
            }
        }
         stage('SonarQube Analysis') {
            steps{
                withSonarQubeEnv('Sonar-Server-10.6') {
                    bat "mvn sonar:sonar"
                }
            }
        }
       
    }
}