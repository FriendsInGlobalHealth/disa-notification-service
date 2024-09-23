FROM eclipse-temurin:11-jdk-alpine AS builder
WORKDIR /opt/app
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN --mount=type=cache,target=/root/.m2 ./mvnw dependency:go-offline
COPY ./src ./src
RUN --mount=type=cache,target=/root/.m2 ./mvnw clean install

FROM eclipse-temurin:11-jre-alpine
RUN ln -s /usr/share/zoneinfo/Africa/Maputo /etc/localtime
WORKDIR /
COPY --from=builder /opt/app/target/*.jar disa-notification-service.jar
ENTRYPOINT ["java", "-jar", "/disa-notification-service.jar"]
