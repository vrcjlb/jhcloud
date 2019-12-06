#!/usr/bin/env groovy

node {
    stage('checkout') {
        checkout scm
    }

    stage('check java') {
        sh "java -version"
    }

    stage('clean') {
        sh "chmod +x mvnw"
        sh "./mvnw -ntp clean"
    }
    stage('nohttp') {
        sh "./mvnw -ntp checkstyle:check"
    }

    stage('backend tests') {
        try {
            sh "./mvnw -ntp verify"
        } catch(err) {
            throw err
        } finally {
            junit '**/target/test-results/**/TEST-*.xml'
        }
    }

    stage('packaging') {
        sh "./mvnw -ntp verify -Pprod -DskipTests"
        archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
    }
    
    stage('build rpm') {
        sh "./mvnw "+
            "-Drevision=${revisionWithSeparator}" +
            "-Drelease=${release} "+
            "-Drpm.name='fast-ebusadaptor' "+
            "-Drpm.user='cbfast' "+
            "-Drpm.group='cbfast' "+
            "rpm:rpm"
        archiveArtifacts artifacts: '**/target/*.rpm', fingerprint: true
    }
    
}
