FROM openjdk:17-jdk-slim AS builder

WORKDIR RecruitmentZoneApplication
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} RecruitmentZoneApplication.jar
RUN java -Djarmode=layertools -jar RecruitmentZoneApplication.jar extract


FROM openjdk:17-jdk-slim
#WORKDIR RecruitmentZoneApplication

COPY --from=builder RecruitmentZoneApplication/dependencies/ ./
COPY --from=builder RecruitmentZoneApplication/spring-boot-loader/ ./
COPY --from=builder RecruitmentZoneApplication/snapshot-dependencies/ ./
COPY --from=builder RecruitmentZoneApplication/application/ ./

# Entry point or command to start your application
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]

