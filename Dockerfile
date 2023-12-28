# Use a lightweight Alpine-based image with Java 21
FROM eclipse-temurin:21-jre-alpine as builder
LABEL authors="justin"
# Set the working directory
WORKDIR /application

# Copy the JAR file from the build stage to the working directory
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar /application/application.jar extract


# Use a smaller runtime image for production
FROM eclipse-temurin:21-jre-alpine

# Set the working directory
WORKDIR /application

# Copy the JAR file from the builder stage to the working directory
COPY --from=builder /application/dependencies/ ./
COPY --from=builder /application/spring-boot-loader/ ./
COPY --from=builder /application/snapshot-dependencies/ ./
COPY --from=builder /application/application/ ./

EXPOSE 8080


# Entry point or command to start your application
ENTRYPOINT ["java", "-Dcom.sun.java.util.concurrent.UseVirtualThreads=true", "org.springframework.boot.loader.JarLauncher"]

# Expose port 8080
