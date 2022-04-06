def parallelStages = [:]
def testPlans = []
def chosenAgent = "master"
def testPlansPath = "ProvarProject/plans"
def planPath = "Regression Plan/Regression"

pipeline {
    agent any
    stages {
        stage('Run Provar Tests') {
            steps {
                script {
                    dir (testPlansPath + "\\" + planPath) {                
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
                                    echo "Running Test Plan ${p} in plan folder ${planPath}"
                                    echo planPath
                                    def testPlan = "${planPath}/${p}"
                                    dir ("${JENKINS_HOME}/workspace/${JOB_NAME}") {
                                        sh "ant \"-Dtest_plan=${testPlan}\" -Dthread=${p} -f ProvarProject/ANT/jenkins_parallel.xml -v"
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
        always {
            junit allowEmptyResults: true, testResults: "ProvarProject/ANT/Results/**/JUnit.xml"
        }
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