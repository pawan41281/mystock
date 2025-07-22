## Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim

# Add the application's jar to the image
COPY target/mystock.jar mystock.jar

# Command to execute the application
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "mystock.jar"]

# Stage 1: Build
#FROM maven:3.9.6-eclipse-temurin-17 AS builder
#COPY . /app
#WORKDIR /app
#RUN mvn clean package -DskipTests
#
## Stage 2: Run
#FROM openjdk:17-jdk-slim
#COPY --from=builder /app/target/mystock.jar app.jar
#ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]
