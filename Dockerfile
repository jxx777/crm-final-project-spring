# Use the slim version of OpenJDK 21 as the base image
FROM openjdk:21-slim as build

# Copy the built JAR file into the container
COPY target/*.jar app.jar

# Command to run the application
ENTRYPOINT ["java","-jar","/app.jar"]