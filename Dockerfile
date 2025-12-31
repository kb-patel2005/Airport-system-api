# Build stage
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/airport-system-0.0.1-SNAPSHOT.jar /app/airport-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "airport-app.jar"]