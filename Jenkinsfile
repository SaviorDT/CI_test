pipeline {
  agent any
  stages {
    stage('get') {
      steps {
        git(url: 'https://github.com/SaviorDT/CI_test.git', branch: 'main')
      }
    }

    stage('test') {
      steps {
        sh '''echo hi
sh \'echo hi\''''
      }
    }

  }
}