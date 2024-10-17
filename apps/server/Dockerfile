FROM maven:3.9.9-eclipse-temurin-17 AS base

FROM base AS dependencies
WORKDIR /usr/src/playground

COPY pom.xml                                                              pom.xml
COPY src/main/java/de/vinado/app/playground/server/ServerApplication.java src/main/java/de/vinado/app/playground/server/ServerApplication.java

RUN mvn -C -q package


FROM base AS builder
WORKDIR /usr/src/playground

COPY --from=dependencies /root/.m2/repository /root/.m2/repository
COPY . .

RUN mvn package


FROM base AS healthcheck-builder
WORKDIR /usr/src/healthcheck

COPY src/docker/Healthcheck.java ./Healthcheck.java
RUN javac -d . Healthcheck.java


FROM eclipse-temurin:17.0.12_7-jre AS runner
WORKDIR /opt/playground

ARG JAR_FILE=server-*.jar

COPY --from=builder             /usr/src/playground/target/${JAR_FILE} ./playground.jar
COPY --from=healthcheck-builder /usr/src/healthcheck/Healthcheck.class  /usr/local/bin/Healthcheck.class

EXPOSE 8080
ENV SERVER_PORT=8080
ENV MANAGEMENT_SERVER_PORT=8081

CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "playground.jar"]
