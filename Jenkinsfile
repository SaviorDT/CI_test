pipeline {
  agent any
  stages {
    stage('get') {
      parallel {
        stage('get') {
          steps {
            git(url: 'https://github.com/SaviorDT/CI_test.git', branch: 'main')
          }
        }

        stage('test') {
          steps {
            bat 'echo hi;'
          }
        }

      }
    }

  }
}