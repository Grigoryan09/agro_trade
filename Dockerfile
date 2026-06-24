FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY . .

RUN mvn clean package -pl web -am -DskipTests

FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=build /app/web/target/*.jar app.jar

# Default DB connection for running the container standalone (not via compose).
# host.docker.internal points to the host machine, where Postgres publishes 5432.
# docker-compose overrides this with postgres:5432 via the `environment:` block.
ENV SPRING_DATASOURCE_URL="jdbc:postgresql://host.docker.internal:5432/agro_trade?currentSchema=agro_trade"
ENV DB_USERNAME="postgres"
ENV DB_PASSWORD="postgres"

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]