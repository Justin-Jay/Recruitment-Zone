FROM eclipse-temurin:21 AS builder

WORKDIR /RecruitmentZone
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} RecruitmentZoneApplication.jar
RUN java -Djarmode=layertools -jar RecruitmentZoneApplication.jar extract

FROM eclipse-temurin:21

COPY --from=builder RecruitmentZone/dependencies/ ./
COPY --from=builder RecruitmentZone/spring-boot-loader/ ./
COPY --from=builder RecruitmentZone/snapshot-dependencies/ ./
COPY --from=builder RecruitmentZone/application/ ./

RUN mkdir -p /Gcloud/Key/

RUN mkdir -p /RecruitmentZoneApplication/Logs/

RUN mkdir -p /RecruitmentZoneApplication/Files/

RUN mkdir -p /RecruitmentZoneApplication/BlogImages/

RUN mkdir -p /RecruitmentZoneApplication/VacancyImages/

# Entry point or command to start your application
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
