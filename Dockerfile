FROM alpine/java:21-jdk
MAINTAINER neqrofukk
COPY target/githubrepo-0.0.1-SNAPSHOT.jar ghapp.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/ghapp.jar"]