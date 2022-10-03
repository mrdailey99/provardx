pipeline {
    agent any
    stages {
        stage('Run Provar Tests') {
            parallel {       
                stage ('Session 1 Tests') {
                    environment {
                        TEST_PLAN = "Regression Plan/Regression/Account"
                    }
                    steps {
                        sh "xvfb-run -a ant -Dtest_plan=${TEST_PLAN} -Dthread=1 -f ANT/jenkins_parallel.xml -v"
                    }
                    post {
                        always {
                            junit allowEmptyResults: true, testResults: 'ANT/Results/1/*.xml'
                            cleanWs notFailBuild: true /* cleans up the workspace */
                        }
                    }
                }
                stage ('Session 2 Tests') {
                    environment {
                        TEST_PLAN = "Regression Plan/Regression/Opportunity"
                    }
                    steps {
                        sh "xvfb-run -a ant -Dtest_plan=${TEST_PLAN} -Dthread=2 -f ANT/jenkins_parallel.xml -v"
                    }
                    post {
                        always {
                            junit allowEmptyResults: true, testResults: 'ANT/Results/2/*.xml'
                            cleanWs notFailBuild: true /* cleans up the workspace */
                        }
                    }
                }                  
            }
        }
    }
    post {
        success {
            echo "Success: All tests passed successfully!"
        }       
        failure {            
            echo 'Failure: Something went wrong with the Provar ANT build. Printing environment for debugging'            
            sh 'printenv'
            echo 'Printing hosts'
            sh 'cat /etc/hosts'
            echo 'Searching for provar directories/files in the system...'
            sh 'find / -name "provar*"'
            echo 'Finding chrome drivers'
            sh "find / -name '*chromedriver*'"
        }        
    }   
}                    