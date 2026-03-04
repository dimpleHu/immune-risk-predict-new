FROM maven:3.8.8-openjdk-8-slim AS build
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn -B -DskipTests clean package

FROM openjdk:8-jre-slim
WORKDIR /app
COPY --from=build /build/target/*.jar app.jar
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "app.jar"]