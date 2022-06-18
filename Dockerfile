FROM eclipse-temurin:8
EXPOSE 8010
ADD target/project1multiarch.jar app.jar
ENTRYPOINT [ "java" , "-jar" , "/app.jar"]