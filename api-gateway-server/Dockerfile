FROM openjdk:17
RUN mkdir "/app"
COPY ./build/libs  /app/
ENTRYPOINT ["java","-jar","api-gateway-server-0.0.1-SNAPSHOT.jar"]