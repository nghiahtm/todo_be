FROM maven:3.9.6-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
LABEL authors="htmn"

FROM openjdk:17-oracle
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]