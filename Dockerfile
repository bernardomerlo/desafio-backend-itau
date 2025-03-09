# Etapa 1: Build da aplicação com Maven (verifique se essa tag existe)
FROM maven:3.8.7-openjdk-18-slim AS build
WORKDIR /app

# Copia o pom.xml e baixa as dependências para aproveitar cache
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código fonte e compila a aplicação
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Criação da imagem final
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copia o JAR gerado da etapa de build
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar backend-itau.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "backend-itau.jar"]
