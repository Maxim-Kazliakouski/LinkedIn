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
                            bat 'mvn clean -DusernameChrome=${USERNAME} -DpasswordChrome=${PASSWORD} test'
                            //bat 'mvn clean -DusernameChrome="valyuk.natali@gmail.com" -DpasswordChrome="valyuk.natali96" test'
                            //-P UI -Dbrowser=$BROWSER \
                            //-DbrowserVersion=$VERSION \
                            //-DvideoTestRecord=$VIDEO_TEST_RECORD \
                            //-Dheadless=$HEADLESS \
                            //-Dqase.username=$USERNAME \
                            //-Dqase.password=$PASSWORD_CREDENTIALS \
                            //-Dtoken=$TOKEN_CREDENTIALS \
                            //-DtestRun=$TESTRUN \
                            //-DcodeProject=$CODEPROJECT

                    } catch (Exception error)
                    {
                        unstable('Testing failed')
                    }
                }
            }

            // To run Maven on a Windows agent, use
            // bat "mvn -Dmaven.test.failure.ignore=true clean package"

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                }
            }
        }
    }
}