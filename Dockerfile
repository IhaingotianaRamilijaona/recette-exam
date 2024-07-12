FROM maven:3.8.5-openjdk-17 as build
COPY . . 
RUN mvn clean -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/recette-0.0.1-SNAPSHOT.jar recette.jar 
EXPOSE 8080
ENTRYPOINT ["java","-jar","recette.jar"]