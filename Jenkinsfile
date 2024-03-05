pipeline {
    agent any
     
    stages {
        stage('Gradle Build') {
            steps {
                // Construye la aplicacion de quarkus
                sh './gradlew build'
            }
        }
        stage('Docker Build') {
            steps{
                // Construye la imagen de la aplicacion de quarkus
                sh 'docker build -f src/main/docker/Dockerfile.jvm -t quarkus/webserviceavvillas-jvm .'
            }
        }
        stage('Docker Previous Container Stop') {
            steps {
                // Detener un contenedor previo, si existe
                sh 'docker stop container_quarkus_avvillas || true' 
            }
        }
        stage('Docker Container Run') {
            steps {
                // Ejecuta un contenedor usando la imagen previa
                sh 'docker run -d --name container_quarkus_avvillas --rm -p 8090:8090 quarkus/webserviceavvillas-jvm '
            }
        }
    }
    post {
        success {
            // Acciones que se ejecutaran solo si el pipeline se completa exitosamente
            echo 'El pipeline se completo exitosamente'
        }
    }
}
