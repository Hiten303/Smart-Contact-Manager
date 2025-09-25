# Use Maven + JDK image to build and run
FROM maven:3.9.2-eclipse-temurin-17 AS build

WORKDIR /app

# Copy all source code
COPY pom.xml .
COPY src ./src

# Build the project
RUN mvn clean package -DskipTests

# ---- Runtime image ----
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]

