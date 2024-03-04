@Library('shared-library')_

pipeline {

    parameters {
            string(name: 'CHAT_ID',                    description: 'chat id of telegram group',            defaultValue: '-1001215679728,-1001640088272')
            string(name: 'NAME_APPS',                    description: 'name apps telegram',            defaultValue: 'agree-logtan-mobile-android')
            string(name: 'CHAT_ID_PRIVATE',                    description: 'chat id of telegram group',            defaultValue: '-1001640088272')
            choice(name: 'ENV', choices: ['Default', 'Dev', 'Staging', 'Prod'], description: 'Select Env to Generate Apk')
    }
    agent none

    options {
        skipDefaultCheckout()
        timeout(time: 30, unit: 'MINUTES')
    }
    stages {
        stage ( "Kill old Build " ){
            steps{
                script{
                    KillOldBuild()
                }
            }
        }
        stage('Pull Repository') {
            agent { label "nodejs" }
            steps {
                checkout scm
                script {
                    echo "get COMMIT_ID"
                    sh 'echo -n $(git rev-parse --short HEAD) > ./commit-id'
                    sh 'echo -n $( git log -1 --pretty=format:%s) > ./commit-status'
                    commitId = readFile('./commit-id')
                    sh "sed -i 's/<//g' commit-status && sed -i 's/>//g' commit-status "
                    commitStatus = readFile('./commit-status')
                    sh "cat ./commit-status"

                }
                stash(name: 'ws', includes:'**,./commit-id')
            }
        }

        stage("Setup Enviroment") {
            agent { label "nodejs" }
            steps {
                cleanWs()
                script{
                    unstash 'ws'
                    def node = tool name: 'NodeJS-10', type: 'jenkins.plugins.nodejs.tools.NodeJSInstallation'
                    env.PATH = "${node}/bin:${env.PATH}"
                    
//                     sh "cp gradle/libs.versions.toml build.versions && sed -i 's/\"//g' build.versions";
                    sh "cp  build.gradle  build.versions && sed -i 's/\"//g' build.versions";
                    def versionInfo = sh ( script: 'cat build.versions  | grep appVersion= | cut -d = -f 2 ',returnStdout: true).trim()

                    if ( params.ENV == "Prod" ) {
                        envBuild = "prod"
                    } else if ( params.ENV == "Staging" ) {
                        envBuild = "staging"
                    } else if ( params.ENV == "Dev" ) {
                        envBuild = "dev"
                    } else if ( params.ENV == "Default" ) {
                        envBuild = ""
                    }

                    sh "echo ParamsEnv : ${params.ENV}"
                    
                    basePath = "base/build/outputs"
                    type = "apk_from_bundle"
                    appName = "Ecosystem-v${versionInfo}"

                    if ( env.BRANCH_NAME == 'master' ){
                        envStage = "production"
                        artifactId= "production"
                        // generate="./gradlew clean assembleRelease appDistributionUploadRelease publishReleaseBundle"

                        if ( params.ENV == "Default" ) {
                            envBuild = "prod"
                            generate="packageProdReleaseUniversalApk"
                            generateDebug= "packageProdDebugUniversalApk"
                            generateAab= "bundleProdRelease"
                        } else {
                            generate="package${params.ENV}ReleaseUniversalApk"
                            generateDebug= "package${params.ENV}DebugUniversalApk"
                            generateAab= "bundle${params.ENV}Release"
                        }

                        unittest = ""
                        output="${basePath}/${type}/${envBuild}Release/${appName}-${envBuild}.apk"
                        outputDebug="${basePath}/${type}/${envBuild}Debug/${appName}-${envBuild}-debug.apk"
                        outputAab="${basePath}/bundle/${envBuild}Release/${appName}-${envBuild}-release.aab"
                        outputFile="${basePath}/${type}/${envBuild}Release/${appName}-${envBuild}-release-universal.apk"
                        outputFileDebug="${basePath}/${type}/${envBuild}Debug/${appName}-${envBuild}-debug-universal.apk"
                    } else if ( env.BRANCH_NAME == 'release' ){
                        envStage = "staging"
                        artifactId= "staging"
                        // generate="./gradlew clean assembleRelease appDistributionUploadStaging"

                        if ( params.ENV == "Default" ) {
                            envBuild = "staging"
                            generate ="packageStagingReleaseUniversalApk"
                            generateDebug= "packageStagingDebugUniversalApk"
                            generateAab= "bundleStagingRelease"
                        } else {
                            generate="package${params.ENV}ReleaseUniversalApk"
                            generateDebug= "package${params.ENV}DebugUniversalApk"
                            generateAab= "bundle${params.ENV}Release"
                        }

                        unittest = ""
                        output="${basePath}/${type}/${envBuild}Release/${appName}-${envBuild}.apk"
                        outputDebug="${basePath}/${type}/${envBuild}Debug/${appName}-${envBuild}-debug.apk"
                        outputFile="${basePath}/${type}/${envBuild}Release/${appName}-${envBuild}-release-universal.apk"
                        outputFileDebug="${basePath}/${type}/${envBuild}Debug/${appName}-${envBuild}-debug-universal.apk"
                    } else if ( env.BRANCH_NAME == 'develop'){
                        envStage = "development"
                        artifactId= "development"
                        // generate= "./gradlew clean assembleRelease appDistributionUploadDebug"

                        if ( params.ENV == "Default" ) {
                            envBuild = "dev"
                            generate="packageDevReleaseUniversalApk"
                            generateDebug= "packageDevDebugUniversalApk"
                            generateAab= "bundleDevRelease"
                        } else {
                            generate="package${params.ENV}ReleaseUniversalApk"
                            generateDebug= "package${params.ENV}DebugUniversalApk"
                            generateAab= "bundle${params.ENV}Release"
                        }

                        output="${basePath}/${type}/${envBuild}Release/${appName}-${envBuild}.apk"
                        outputDebug="${basePath}/${type}/${envBuild}Debug/${appName}-${envBuild}-debug.apk"
                        outputFile="${basePath}/${type}/${envBuild}Release/${appName}-${envBuild}-release-universal.apk"
                        outputFileDebug="${basePath}/${type}/${envBuild}Debug/${appName}-${envBuild}-debug-universal.apk"
                        // unittest=" ./gradlew jacocoFullReport"
                        unittest = ""
                    } else {
                        envStage = "Development"
                        artifactId= "development-${env.BRANCH_NAME}"
                    }
                    withCredentials([usernamePassword(credentialsId: 'nexus-credential', usernameVariable: 'username', passwordVariable: 'password')]) {
                        echo "Download Keystore / credential"
                        sh "wget --user=${username} --password=${password} -O ecosystem-keystore.jks https://nexus.playcourt.id/repository/agree-logtan/agree-logtan/ecosystem/keystore/ecosystem-keystore.txt"
                        sh "wget --user=${username} --password=${password} -O keystore.properties https://nexus.playcourt.id/repository/agree-logtan/agree-logtan/ecosystem/properties/ecosystem-properties.properties"
                        sh "wget --user=${username} --password=${password} -O legion.properties https://nexus.playcourt.id/repository/agree-logtan/agree-logtan/legion/properties/legion-properties.properties"
                        legionProperties = readFile("./legion.properties").trim()
                        sh "echo -n '${legionProperties}' >> ./gradle.properties"
                        sh "cat ./gradle.properties"
                        // sh "cp legion.properties ~/.gradle/gradle.properties"
                        // sh "wget --user=${username} --password=${password} -O keystore.properties http://nexus3-nexus3-playcourt.vsan-apps.playcourt.id/repository/agree-logtan/agree-logtan/fishery/properties/fishery-properties.properties"

                        sh "cat keystore.properties"
                        //sh "wget --user=${username} --password=${password} -O logee-trans-credential-distribution.json http://nexus3-nexus3-playcourt.vsan-apps.playcourt.id/repository/logee/logee-trans/${artifactId}/env/${artifactId}-env.txt"
                    }
                    stash(name: 'ws', includes:'**')
                }
            }
        }

        stage('Test & Generate') {
            parallel {    
                stage('SonarQube Scan') {
                    agent {
                        docker {
                            image "playcourt/jenkins:android-30"
                            label "jenkins-agent-docker-1"
                            args '-u root -v /var/lib/jenkins/:/var/lib/jenkins/'
                        }
                    }
                    steps {
                        sh "rm -Rf *"
                        unstash 'ws'
                        echo "Run SonarQube"
                        script {
                            // echo "defining sonar-scanner"
                            // sh "rm -rf ~/.gradle"
                            sh "df -h"
                            sh "${unittest}"
                            def scannerHome = tool 'SonarScanner' ;
                            withSonarQubeEnv('SonarQube') {
                                sh "${scannerHome}/bin/sonar-scanner"
                            }
                        }
                    }
                }
            
                stage('Generate Apk Release') {
                    agent {
                        docker {
                            image "playcourt/jenkins:android-33"
//                             image "oratakashi/android-sdk:33"
                            label "jenkins-agent-docker-1"
                            args '-u root -v /var/lib/jenkins/:/var/lib/jenkins/'
                        }
                    }
                    // environment { 
                    //     GOOGLE_APPLICATION_CREDENTIALS = "${WORKSPACE}/logee-trans-credential-distribution.json"
                    // }
                    steps {
                        script {
                            try {
                                sh "rm -R *"
                            } catch (err) {
                                echo "Error: ${err}"
                            }
                        }
                        unstash 'ws'
                        echo "Run Generate APK Release"
                        script {
                            withCredentials([usernamePassword(credentialsId: 'nexus-credential', usernameVariable: 'username', passwordVariable: 'password')]) {
                                sh "which java"
                                sh "./gradlew ${generate} -Pnexus_username=${username} -Pnexus_password=${password}"
                                sh "mv ${outputFile} ${output}"
                            }

                            withCredentials([string(credentialsId: 'new-telegram-token', variable: 'TELEGRAM_TOKEN')]) {
                                sh "curl -F document=@\"${output}\" https://api.telegram.org/bot${TELEGRAM_TOKEN}/sendDocument?chat_id=${params.CHAT_ID_PRIVATE}"
                            }
                        }

//                         stash(name: 'ws', includes:'**')
                    }
                }

                stage('Generate Apk Debug') {
                    agent {
                        docker {
                            image "playcourt/jenkins:android-33"
//                             image "oratakashi/android-sdk:33"
                            label "jenkins-agent-docker-1"
                            args '-u root -v /var/lib/jenkins/:/var/lib/jenkins/'
                        }
                    }
                    // environment { 
                    //     GOOGLE_APPLICATION_CREDENTIALS = "${WORKSPACE}/logee-trans-credential-distribution.json"
                    // }
                    steps {
                        script {
                            try {
                                sh "rm -R *"
                            } catch (err) {
                                echo "Error: ${err}"
                            }
                        }
                        unstash 'ws'
                        echo "Run Generate APK Release"
                        script {
                            withCredentials([usernamePassword(credentialsId: 'nexus-credential', usernameVariable: 'username', passwordVariable: 'password')]) {
                                sh "which java"
                                sh "./gradlew ${generateDebug} -Pnexus_username=${username} -Pnexus_password=${password}"
                                sh "mv ${outputFileDebug} ${outputDebug}"
                            }

                            withCredentials([string(credentialsId: 'new-telegram-token', variable: 'TELEGRAM_TOKEN')]) {
                                sh "curl -F document=@\"${outputDebug}\" https://api.telegram.org/bot${TELEGRAM_TOKEN}/sendDocument?chat_id=${params.CHAT_ID_PRIVATE}"
                            }
                        }

//                         stash(name: 'ws', includes:'**')
                    }
                }

                stage('Generate AAB') {
                    agent {
                        docker {
                            image "playcourt/jenkins:android-33"
//                             image "oratakashi/android-sdk:33"
                            label "jenkins-agent-docker-1"
                            args '-u root -v /var/lib/jenkins/:/var/lib/jenkins/'
                        }
                    }
                    // environment {
                    //     GOOGLE_APPLICATION_CREDENTIALS = "${WORKSPACE}/logee-trans-credential-distribution.json"
                    // }
                    when {
                        branch 'master'
                    }
                    steps {
                        script {
                            try {
                                sh "rm -R *"
                            } catch (err) {
                                echo "Error: ${err}"
                            }
                        }
                        unstash 'ws'
                        echo "Run Generate AAB"
                        script {
                            withCredentials([usernamePassword(credentialsId: 'nexus-credential', usernameVariable: 'username', passwordVariable: 'password')]) {
                                sh "which java"
                                sh "./gradlew ${generateAab}  -Pnexus_username=${username} -Pnexus_password=${password}"
                                // sh "mv ${outputFileDebug} ${outputDebug}"
                            }

                            withCredentials([string(credentialsId: 'new-telegram-token', variable: 'TELEGRAM_TOKEN')]) {
                                sh "curl -F document=@\"${outputAab}\" https://api.telegram.org/bot${TELEGRAM_TOKEN}/sendDocument?chat_id=${params.CHAT_ID_PRIVATE}"
                            }
                        }

                        stash(name: 'ws', includes:'**')
                    }
                }
            }
        }

        stage('Send to telegram ') {
            agent { label "nodejs" }
            steps {
                unstash 'ws'
                echo "Send to Telegram"
                script {
                    //  sh "./gradlew packageRelease"
                    withCredentials([string(credentialsId: 'new-telegram-token', variable: 'TELEGRAM_TOKEN')]) {
                        sh "ls -alh"
                        commitStatus = readFile('./commit-status')
                        sh "curl -s -X POST 'https://api.telegram.org/bot${TELEGRAM_TOKEN}/sendMessage?chat_id=${params.CHAT_ID_PRIVATE}&text=%5b%20New Changes%20%5d%20${commitStatus}&parse_mode=HTML'"
                    }

                }
            }
        }
    }

    post {
        always{
            node("Docker"){ 
                TelegramNotif(currentBuild.currentResult) 
            }   
        } 
    }
}


