#
# Build stage
#
FROM maven:3.8.1 AS build
COPY src /home/app/src
COPY pom.xml /home/app
COPY application*.properties /home/app/src/main/resources/
RUN mvn -f /home/app/pom.xml clean package
WORKDIR /home/app

#
# Package stage
#


FROM adoptopenjdk/openjdk11:alpine-jre
# Refer to Maven build -> finalName
ARG JAR_FILE=/home/app/target/NimapCvBuild-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app
COPY --from=build /home/app/src/main/resources/application*.properties /opt/app/

COPY --from=build ${JAR_FILE} app.jar

ENTRYPOINT ["java" ,"-Djava.security.egd=file:/dev/./urandom --spring.profiles.active=dev --spring.config.location=/opt/app/application.properties","-jar","app.jar"]