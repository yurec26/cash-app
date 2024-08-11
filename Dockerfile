FROM  openjdk:17-jdk-alpine
EXPOSE 5500
COPY target/cash_app-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]