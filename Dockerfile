# Estágio 1: Build da aplicação usando Maven com Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -DskipTests

# Estágio 2: Execução da aplicação com Java 21 (imagem leve alpine)
FROM eclipse-temurin:21-jdk-alpine
COPY --from=build /target/dslist-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]