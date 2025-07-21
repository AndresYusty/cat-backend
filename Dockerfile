# Stage 1: build with Maven + JDK 17
FROM maven:3.9.3-eclipse-temurin-17 AS builder
WORKDIR /app

# Copiamos solo los archivos de build primero (para cache)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiamos el código fuente y compilamos
COPY src ./src
RUN mvn clean package -DskipTests -B

# Stage 2: runtime con JRE 17
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copiamos el JAR desde el builder
COPY --from=builder /app/target/cat-backend-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto de la app
EXPOSE 8080

# Comando de arranque
ENTRYPOINT ["java","-jar","/app/app.jar"]
