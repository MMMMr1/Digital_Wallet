FROM gradle:jdk17 as gradleimage
COPY . /home/gradle/source
WORKDIR /home/gradle/source

FROM openjdk:17
WORKDIR /app
COPY --from=gradleimage /home/gradle/source/build/libs/configuration-server-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java","-jar","configuration-server-0.0.1-SNAPSHOT.jar"]