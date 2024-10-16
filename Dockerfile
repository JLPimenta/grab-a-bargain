FROM maven:3.8.5-openjdk-17-slim as build
WORKDIR /app
COPY . .

RUN mvn clean package -Dprod -DskipTests

FROM openjdk:24-jdk-slim
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
EXPOSE 5432

ENTRYPOINT ["java", "-jar", "app.jar"]