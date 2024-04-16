FROM eclipse-temurin:17-jre AS builder

WORKDIR /RecruitmentZone
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} RecruitmentZoneApplication.jar
RUN java -Djarmode=layertools -jar RecruitmentZoneApplication.jar extract

FROM eclipse-temurin:17-jre

COPY --from=builder RecruitmentZone/dependencies/ ./
COPY --from=builder RecruitmentZone/spring-boot-loader/ ./
COPY --from=builder RecruitmentZone/snapshot-dependencies/ ./
COPY --from=builder RecruitmentZone/application/ ./

RUN mkdir -p /RecruitmentZoneApplication/Logs/

RUN mkdir -p /RecruitmentZoneApplication/Files/

# Entry point or command to start your application
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
