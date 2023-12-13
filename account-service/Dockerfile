FROM gradle:jdk17 as gradleimage
COPY . /home/gradle/source
WORKDIR /home/gradle/source
ENV DOCKER_HOST=tcp://host.docker.internal:2375
ENV TESTCONTAINERS_HOST_OVERRIDE=host.docker.internal
RUN gradle

FROM openjdk:17
WORKDIR /app
COPY --from=gradleimage /home/gradle/source/build/libs/account-service-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java","-jar","account-service-0.0.1-SNAPSHOT.jar"]