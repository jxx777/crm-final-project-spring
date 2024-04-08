# Use a Maven image with JDK 21 if available, otherwise use the latest and install JDK 21 manually
FROM amazoncorretto as build

# Copy the project files to the container
COPY src /home/app/src
COPY pom.xml /home/app

# Set the working directory for following commands
WORKDIR /home/app

# Build the application
RUN mvn clean package

# Start a new stage to create a slim final image
FROM openjdk:21-jdk-slim

# Copy the JAR file from the build stage to the final image
COPY --from=build /home/app/target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]