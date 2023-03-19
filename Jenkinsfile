pipeline {
    
    agent any

    stages {
        
        stage("Build"){
            steps{
                echo("build the project")
            }
        }
        
        stage("Deploy to Dev"){
            steps{
                echo("deployed to dev env ")
            }
        }
        
        stage("Deploy to QA"){
            steps{
                echo("deployed to dev QA env ")
            }
        }
        
           stage("Run regression test cases"){
            steps{
                echo("Run regression automation test cases")
            }
        }
        
        stage("Deploy to stage"){
            steps{
                echo("deployed to dev stage env ")
            }
        }
        
        stage("Run sanity test cases"){
            steps{
                echo("Run sanity automation test cases")
            }
        }
        
         stage("Deploy to prod"){
            steps{
                echo("deployed to dev prod env ")
            }
        }
        
    }
    
}