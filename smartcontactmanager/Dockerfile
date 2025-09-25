# Use a lightweight JDK image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory inside container
WORKDIR /app

# Copy the built JAR into the container
COPY target/*.jar app.jar

# Expose port 8080 (Render will override with PORT env)
EXPOSE 8080

# Start the Spring Boot application
CMD ["java", "-jar", "app.jar"]
