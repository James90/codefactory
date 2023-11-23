FROM openjdk:17-alpine

WORKDIR /app

COPY target/codefactory-0.0.2.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]