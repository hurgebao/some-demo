FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD docker-demo.jar dockerdemo.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/dockerdemo.jar"]