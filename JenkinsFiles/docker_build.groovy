#!groovy

properties([disableConcurrentBuilds()])

pipeline {
    agent {
        label 'master'
    }
    triggers { pollSCM ('H * * * *') }
    options {
        buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '10'))
        timestamps()
    }
    stages {
        stage("Docker login") {
            step {
                echo '============== Docker login ============='
                withCredentials([usernamePassword(credentialsId: 'Docker_Hub', passwordVariable: 'pass', usernameVariable: 'user')]) {
                    // the code in here can access $pass and $user
                    sh """
                    docker login -u $user -p $pass
                    """
                    }
                }
        }
        stage("Create docker image") {
            steps {
                echo  '============== Start the script ============='
                dir ('docker/dockerfile'){
                    sh 'docker build -t smirnoff/docker-tag:latest .'
                }
            }
        }
        stage("Docker push") {
            step {
                echo  '============== Push docker image ============='
                sh """
                docker push smirnoff/docker-tag:latest
                """
            }
        }
    }
}
