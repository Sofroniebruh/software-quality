pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK17'
    }

    triggers {
            githubPush()
        }

    environment {
        MAVEN_OPTS = "-Xmx1024m"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'dev', url: 'https://github.com/Sofroniebruh/software-quality'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                script {
                        sh 'mvn test'
                     }
                }
                post {
                     always {
                                 junit 'target/surefire-reports/*.xml'
                     }
                }
        }

        stage('Code Analysis') {
            steps {
                sh 'mvn verify'
            }
        }

        stage('Push to Test if Tests Pass') {
                    steps {
                        script {
                            withCredentials([usernamePassword(credentialsId: 'github-credentials-for-sofronie-account', usernameVariable: 'GIT_USER', passwordVariable: 'GIT_PASS')]) {
                                sh '''
                                git config --global user.email "jenkins@example.com"
                                git config --global user.name "Jenkins CI"
                                git remote set-url origin https://${GIT_USER}:${GIT_PASS}@github.com/Sofroniebruh/software-quality.git
                                git checkout test
                                git merge dev
                                git push origin test
                                '''
                            }
                        }
                    }
                }
    }

    post {
            failure {
                echo 'Build failed. Check logs for more details.'
            }
            success {
                echo 'Build successful!'
            }
    }
}

