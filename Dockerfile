FROM openjdk:8
EXPOSE 8010
ADD target/project1buildlocal.jar app.jar
ENTRYPOINT [ "java" , "-jar" , "/app.jar"]