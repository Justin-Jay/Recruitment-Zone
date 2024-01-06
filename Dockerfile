FROM eclipse-temurin:17-jre-jammy AS builder

# The rest of your Dockerfile...

WORKDIR RecruitmentZoneApplication
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} RecruitmentZoneApplication.jar
RUN java -Djarmode=layertools -jar RecruitmentZoneApplication.jar extract


FROM eclipse-temurin:21-jre
WORKDIR RecruitmentZoneApplication
# create directories
# Add a timestamp as a comment

COPY --from=builder RecruitmentZoneApplication/dependencies/ ./
COPY --from=builder RecruitmentZoneApplication/spring-boot-loader/ ./
COPY --from=builder RecruitmentZoneApplication/snapshot-dependencies/ ./
COPY --from=builder RecruitmentZoneApplication/application/ ./
# Entry point or command to start your application
ENTRYPOINT ["java", "-Dcom.sun.java.util.concurrent.UseVirtualThreads=true","org.springframework.boot.loader.JarLauncher"]

EXPOSE 8080