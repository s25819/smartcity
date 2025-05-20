FROM eclipse-temurin:17-jdk

WORKDIR /app

RUN apt-get update && apt-get install -y maven curl

COPY pom.xml ./
RUN mvn dependency:go-offline

COPY src ./src

ENV MAVEN_OPTS="-Dspring.devtools.restart.enabled=true"

# Start aplikacji
CMD ["mvn", "spring-boot:run"]