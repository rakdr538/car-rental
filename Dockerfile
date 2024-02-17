FROM amazoncorretto:21-alpine-jdk
MAINTAINER Rakesh Dronamraj
COPY target/car-rental-0.0.1-SNAPSHOT.jar car-rental-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/car-rental-0.0.1-SNAPSHOT.jar"]