FROM amazoncorretto:11
EXPOSE 8082
ADD target/claim-0.0.1-SNAPSHOT.jar claimapp.jar
ENTRYPOINT ["java","-jar","/claimapp.jar"]