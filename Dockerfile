FROM openjdk:8-jdk-alpine
EXPOSE 8080
COPY /target/hackerNewsDemo-0.0.1-SNAPSHOT.jar hackerNewsDemo-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","/hackerNewsDemo-0.0.1-SNAPSHOT.jar"]