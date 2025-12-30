# Build stage
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/demo-0.0.1-airport-system.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]