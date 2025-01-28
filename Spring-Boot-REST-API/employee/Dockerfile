# Use an OpenJDK image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/employee-0.0.1-SNAPSHOT.jar app.jar

# Copy the wait script
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

# Expose the port used by the application
EXPOSE 8082

# Command to run the application
ENTRYPOINT ["/wait-for-it.sh", "db:3307", "java", "-jar", "app.jar"]