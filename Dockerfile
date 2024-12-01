# Use an official OpenJDK image as a parent image
FROM amazoncorretto:21.0.4-alpine
# Set the working directory in the container
WORKDIR /app

# Copy the jar file of your Spring Boot application into the container
COPY target/*.jar /app/app.jar

# Expose the port your application will run on
EXPOSE 8080

# Set the entry point to run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]