FROM maven:3-eclipse-temurin-17-alpine AS build

WORKDIR /build
COPY . /build

RUN mvn package

FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=build /build/target/*.jar /app/book-catalog.jar

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "book-catalog.jar"]