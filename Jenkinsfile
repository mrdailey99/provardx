def parallelStages = [:]
def testPlans = []
def chosenAgent = "master"

pipeline {
    agent any
    stages {
        stage('Run Provar Tests') {
            steps {
                cd "ProvarProject/plans/Regression Plan/Regression"
                script {
                    def plans = findPlans()
                    plans.each { p ->
                        if (p.directory) {
                            testPlans.add(p.name)
                        }
                    }

                    testPlans.each { p ->
                        parallelStages[p] = {
                            node {
                                dir(p) {
                                    stage(p) {
                                        echo p
                                        export TEST_PLAN="Regression Plan/Regression/${p}" 
                                        sh "xvfb-run ant -Dthread=${p} -f ProvarProject/ANT/jenkins_parallel.xml -v"
                                    }
                                    post {
                                        always {
                                            junit allowEmptyResults: true, testResults: "ANT/Results/${p}/*.xml"
                                            cleanWs notFailBuild: true /* cleans up the workspace */
                                        }
                                    }                                
                                }
                            }
                        } 
                    }

                    parallel parallelStages
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