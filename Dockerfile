FROM openjdk:17-jdk-slim AS builder

# The rest of your Dockerfile...

WORKDIR RecruitmentZoneApplication
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} RecruitmentZoneApplication.jar
RUN java -Djarmode=layertools -jar RecruitmentZoneApplication.jar extract


FROM openjdk:17-jdk-slim
WORKDIR RecruitmentZoneApplication
# create directories
# Add a timestamp as a comment

COPY --from=builder RecruitmentZoneApplication/dependencies/ ./
COPY --from=builder RecruitmentZoneApplication/spring-boot-loader/ ./
COPY --from=builder RecruitmentZoneApplication/snapshot-dependencies/ ./
COPY --from=builder RecruitmentZoneApplication/application/ ./

EXPOSE 8080
# Entry point or command to start your application
ENTRYPOINT ["java", "-Dcom.sun.java.util.concurrent.UseVirtualThreads=true","org.springframework.boot.loader.JarLauncher"]