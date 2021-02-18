FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD
ARG SPRING_ACTIVE_PROFILE
MAINTAINER Jasmin
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn clean install -Dspring.profiles.active=$SPRING_ACTIVE_PROFILE && mvn package -B -e -Dspring.profiles.active=$SPRING_ACTIVE_PROFILE

FROM openjdk:8-alpine
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/appdemo-*.jar /app/appdemo.jar
ENTRYPOINT ["java", "-jar", "appdemo.jar"]

