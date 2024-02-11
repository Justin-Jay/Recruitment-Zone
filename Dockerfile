FROM eclipse-temurin:17-jre AS builder

WORKDIR /RecruitmentZoneApplication
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} RecruitmentZoneApplication.jar
RUN java -Djarmode=layertools -jar RecruitmentZoneApplication.jar extract

RUN mkdir /RecruitmentZoneApplication/ApplicationFiles/

FROM eclipse-temurin:17-jre

COPY --from=builder RecruitmentZoneApplication/dependencies/ ./
COPY --from=builder RecruitmentZoneApplication/spring-boot-loader/ ./
COPY --from=builder RecruitmentZoneApplication/snapshot-dependencies/ ./
COPY --from=builder RecruitmentZoneApplication/application/ ./

# Entry point or command to start your application
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]

