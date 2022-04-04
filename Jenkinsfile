def parallelStages = [:]
def testPlans = []
def chosenAgent = "master"
def planPath = "ProvarProject/plans/Regression Plan/Regression"

pipeline {
    agent any
    stages {
        stage('Run Provar Tests') {
            steps {
                script {
                    dir (planPath) {                
                        def plans = findFiles()
                        plans.each { p ->
                            if (p.directory) {
                                testPlans.add(p.name)
                            }
                        }
                    }

                    testPlans.each { p ->
                        parallelStages[p] = {
                            node {
                                stage(p) {
                                    echo p
                                    echo planPath
                                    echo `pwd`
                                    sh "ant -Dtest_plan=${planPath}/${p} -Dthread=${p} -f ProvarProject/ANT/jenkins_parallel.xml -v"
                                }
                                post {
                                    always {
                                        junit allowEmptyResults: true, testResults: "ProvarProject/ANT/Results/${p}/*.xml"
                                        cleanWs notFailBuild: true /* cleans up the workspace */
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
            // echo 'Printing hosts'
            // sh 'cat /etc/hosts'
            // echo 'Searching for provar directories/files in the system...'
            // sh 'find / -name "provar*"'
            // echo 'Finding chrome drivers'
            // sh "find / -name '*chromedriver*'"
        }        
    }   
}                    