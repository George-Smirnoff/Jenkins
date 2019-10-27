#!groovy

properties([disableConcurrentBuilds()])

pipeline {
    agent {
        label 'master'
    }
    triger { pollSCM ('*****') }
    options {
        buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '10'))
        timestamps()
    }
    stages {
        stage("Create doccker image") {
            steps {
                echo  '============== Start the script ============='
                dir ('docker/dockerfile'){
                    sh 'docker build .'
                }
            }
        }

    }
}
