# Étape 1 : Compilation (Build)
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

# 1. Copie des scripts Gradle
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Donner les droits d'exécution au wrapper Gradle
RUN chmod +x ./gradlew

# 2. Téléchargement des dépendances en amont (permet d'utiliser le cache Docker)
RUN ./gradlew dependencies --no-daemon || true

# 3. Copie du code source et génération du JAR (en ignorant les tests pour aller plus vite)
COPY src src
RUN ./gradlew bootJar --no-daemon -x test

# Étape 2 : Exécution (Runtime)
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Sécurité : Création et utilisation d'un utilisateur non-root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copie du JAR produit lors de l'étape de build
COPY --from=builder /app/build/libs/*.jar app.jar

# Port exposé par l'application Spring Boot
EXPOSE 8080

# Options JVM (gestion de la mémoire conteneurisée) et lancement de l'application
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]