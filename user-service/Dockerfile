FROM openjdk:17
RUN mkdir "/app"
COPY ./build/libs  /app/
ENTRYPOINT ["java","-jar","user-service-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080