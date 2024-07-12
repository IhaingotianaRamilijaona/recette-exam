FROM maven:3.8.5-openjdk-17 as build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn package -DskipTests

FROM openjdk:17.0.1
COPY --from=build /app/target/recette-0.0.1-SNAPSHOT.jar recette.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","recette.jar"]
