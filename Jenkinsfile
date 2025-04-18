pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }
        
        stage('Test') {
            steps {
                sh 'mvn test -Dheadless=true'
            }
            post {
                always {
                    junit '**/test-output/junitreports/*.xml'
                    publishHTML([
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'test-output/extent-reports',
                        reportFiles: 'ExtentReport_*.html',
                        reportName: 'Extent Reports',
                        reportTitles: 'Extent Reports'
                    ])
                    archiveArtifacts artifacts: 'screenshots/**/*.png', allowEmptyArchive: true
                }
            }
        }
    }
    
    post {
        success {
            echo 'Tests executed successfully!'
        }
        failure {
            echo 'Tests failed!'
        }
    }
} 