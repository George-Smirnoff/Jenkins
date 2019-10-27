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
        stage("Create doccker image") {
            steps {
                echo  '============== Start the script ============='
                dir ('docker/dockerfile'){
<<<<<<< HEAD
                    sh 'docker build -t docker-toolbox:latest .'
=======
                    sh 'docker build -t docker-tag:latest .'
>>>>>>> temp-branch
                }
            }
        }

    }
}
