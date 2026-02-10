# Using JDK 17 to build and run Spring Boot
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy JAR from target
COPY target/portfolio-backend-0.0.1-SNAPSHOT.jar app.jar

# Open the port on which Spring Boot listens
EXPOSE 8080

# Open the port on which Spring Boot listens
EXPOSE 8080

# Start JAR
ENTRYPOINT ["java", "-jar", "app.jar"]