FROM openjdk:17-slim
WORKDIR /app
COPY target/backend-spring-boot-project.jar /app
ENTRYPOINT ["java", "-jar", "backend-spring-boot-project.jar"]