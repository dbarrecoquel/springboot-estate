FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

# Installer Maven
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

# Copier seulement les pom.xml pour profiter du cache Docker
COPY pom.xml .
COPY user/pom.xml user/pom.xml
COPY ads/pom.xml ads/pom.xml
COPY backoffice/pom.xml backoffice/pom.xml
COPY frontrest/pom.xml frontrest/pom.xml

# Télécharger les dépendances avec retry
RUN mvn dependency:go-offline -DskipTests || \
    mvn dependency:resolve -DskipTests || \
    mvn dependency:resolve-plugins -DskipTests || \
    true

# Copier le code source

COPY user user
COPY ads ads
COPY backoffice backoffice
COPY frontrest frontrest


# Build avec retry
RUN mvn clean install -DskipTests -o || \
    mvn clean install -DskipTests || \
    (sleep 10 && mvn clean install -DskipTests)

# Image de production
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copier les JARs construits
COPY --from=build /app/backoffice/target/*.jar backoffice.jar
COPY --from=build /app/frontrest/target/*.jar frontrest.jar


# Copier le script de démarrage
COPY docker-entrypoint.sh /app/
RUN chmod +x /app/docker-entrypoint.sh

EXPOSE 8081 8082

ENTRYPOINT ["/app/docker-entrypoint.sh"]