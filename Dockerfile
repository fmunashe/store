# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jre

# Set the working directory in the container
WORKDIR /app

# Copy the Gradle wrapper and the Gradle build file
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .

# Copy the compiled jar file into the container
COPY build/libs/*.jar store.jar

# Expose port 8080 (default for Spring Boot)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "store.jar"]
