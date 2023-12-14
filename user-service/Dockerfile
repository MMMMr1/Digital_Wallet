FROM gradle:jdk17 as gradleimage
COPY . /home/gradle/source
WORKDIR /home/gradle/source

ENV DOCKER_HOST=tcp://host.docker.internal:2375
RUN gradle build
#docker run -p 2375:2375 -v /var/run/docker.sock:/var/run/docker.sock alpine/socat tcp-listen:2375,reuseaddr,fork unix-connect:/var/run/docker.sock

FROM openjdk:17
WORKDIR /app
COPY --from=gradleimage /home/gradle/source/build/libs/user-service-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java","-jar","user-service-0.0.1-SNAPSHOT.jar"]