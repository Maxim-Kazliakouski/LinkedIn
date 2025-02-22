pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "M2"
    }
//     triggers {
//         cron('0 8 * * *')
//     }
    parameters {
        gitParameter ( branch: '',
        branchFilter: 'origin/(.*)',
        defaultValue: 'master',
        description: '',
        name: 'BRANCH',
        quickFilterEnabled: true,
        selectedValue: 'NONE',
        sortMode: 'NONE',
        tagFilter: '*',
        type: 'PT_BRANCH' )
    }

    stages {

        stage('Performing tests...') {

            steps {

                script {

                    try {

                        // Get some code from a GitHub repository
                        git branch: "${params.BRANCH}",  url: 'https://github.com/Maxim-Kazliakouski/LinkedIn.git'

                        //withCredentials ([
                        //    string(credentialsId: 'qase_token',
                        //variable: 'TOKEN_CREDENTIALS'),
                        //    string(
                        //        credentialsId: 'qase_password',
                        //        variable: 'PASSWORD_CREDENTIALS')
                        //])


                            // Run Maven on a Unix agent.
                            bat "mvn clean -DusernameChrome=$USERNAME -DpasswordChrome=$PASSWORD -DspecialitiesForAdding=$SPECIALITIES test"
                    } catch (Exception error)
                    {
                        unstable('Testing failed')
                    }
                }
            }

            // To run Maven on a Windows agent, use
            // bat "mvn -Dmaven.test.failure.ignore=true clean package"

            post {
                always {
                    echo 'Sending email...'
                        mail to: 'maxim.kazliakouski@gmail.com',
                        subject: "Jenkins build === ${currentBuild.currentResult} === ${env.JOB_NAME}",
                        body: """${currentBuild.currentResult}: Job ${env.JOB_NAME} \nMore Info about build can be found here: ${env.BUILD_URL}"""

            }
        success {
            junit '**/target/surefire-reports/TEST-*.xml'
                }
            }
        }
    }
}