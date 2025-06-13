pipeline {
    agent any

    tools {
        maven 'Maven 3.9.10'   // Name must match Jenkins > Global Tool Configuration
        jdk 'Java 17'         // Name must match Jenkins > Global Tool Configuration
    }

    stages {
        stage('Checkout Code') {
            steps {
                git 'https://github.com/ballavenusree/FarmEasy.git'  // Git repo URL
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }
    }

    post {
        success {
            echo 'Build and Test Completed Successfully!'
        }
        failure {
            echo 'Build or Tests Failed!'
        }
    }
}
