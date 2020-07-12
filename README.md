# The Following repository starts a spring boot application with influx db to store the metrics 

### Steps to run:

* mvn clean install -DskipTests
* Move metric-0.0.1-SNAPSHOT.jar to docker folder 
* cd to docker directory
* docker-compose build
* docker-compose up
* go to localhost:8085/swagger-ui/index.html

### Run Class

* [run](src/main/java/com/example/experiment/metric/runner/MetricExperimentApplication.java)