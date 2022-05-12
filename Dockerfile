FROM openjdk:16
COPY target/rebeldes-api-0.0.1-SNAPSHOT.jar rebeldes-api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/rebeldes-api-0.0.1-SNAPSHOT.jar"]