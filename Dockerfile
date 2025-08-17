FROM eclipse-temurin:17-jdk-alpine
LABEL authors="Siduska"
WORKDIR /app
COPY target/e-health-wallet-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]