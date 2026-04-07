pipeline {
    agent any

    environment {
        JAVA_HOME = 'C:\\Program Files\\Java\\jdk-21'
        MAVEN_HOME = 'C:\\Users\\HP\\.maven\\maven-3.9.14'
        PATH = "${MAVEN_HOME}\\bin;${JAVA_HOME}\\bin;${PATH}"
        SONAR_HOST_URL = 'http://localhost:9000'
        SONAR_LOGIN = credentials('sonarqube-token')
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out code...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Building project with Maven...'
                bat 'mvn clean compile -DskipTests'
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                bat 'mvn test'
            }
        }

        stage('Code Quality Analysis') {
            steps {
                echo 'Running SonarQube analysis...'
                bat """
                    mvn sonar:sonar ^
                    -Dsonar.projectKey=jmenu-task ^
                    -Dsonar.sources=. ^
                    -Dsonar.host.url=%SONAR_HOST_URL% ^
                    -Dsonar.login=%SONAR_LOGIN%
                """
            }
        }

        stage('Build Artifacts') {
            steps {
                echo 'Creating project artifacts...'
                bat 'mvn package -DskipTests'
            }
        }
    }

    post {
        always {
            node {
                echo 'Pipeline execution completed'
                // Collect JUnit reports from all modules
                junit '**/target/surefire-reports/*.xml'
            }
        }
        success {
            echo 'Build and analysis successful!'
        }
        failure {
            echo 'Build or analysis failed. Check logs for details.'
        }
    }
}