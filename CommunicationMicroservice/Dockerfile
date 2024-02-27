FROM openjdk:17
EXPOSE 8082
COPY target/monitoring-communication-microservice-0.0.1-SNAPSHOT.jar consumer.jar
ENTRYPOINT ["java","-jar","/consumer.jar"]
