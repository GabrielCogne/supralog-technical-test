FROM eclipse-temurin:21-jdk-jammy AS development

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN ./mvnw dependency:resolve

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]

FROM eclipse-temurin:21-jdk-jammy AS build

WORKDIR /app

COPY --from=development /app/.mvn/ .mvn
COPY --from=development /app/mvnw /app/pom.xml ./
COPY --from=development /app/src ./src

RUN ./mvnw clean package

FROM eclipse-temurin:21-jdk-jammy AS production

WORKDIR /app

COPY --from=build /app/target/supralog-technical-test-1.0.0-RELEASE.jar ./

CMD ["java", "-jar", "supralog-technical-test-1.0.0-RELEASE.jar"]