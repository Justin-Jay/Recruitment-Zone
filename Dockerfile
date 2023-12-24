# syntax=docker/dockerfile:1

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /recruitment-zone

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]
