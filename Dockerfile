# Use a lightweight Java image
FROM openjdk:17-jdk-slim

# Set working directory inside container
WORKDIR /app

# Copy the JAR file to the container
COPY target/Exam-application-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 (same as your Spring Boot app)
EXPOSE 8081

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
