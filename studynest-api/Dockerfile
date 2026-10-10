# Multi-stage build for StudyNest Seat Booking Portal
# Stage 1: Build JAR
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Minimal JRE Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/studynest-api-1.0.0.jar app.jar

ENV PORT=8080
EXPOSE 8080

# Reads dynamic PORT environment variable (required by Render/Railway) or defaults to 8080
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]
