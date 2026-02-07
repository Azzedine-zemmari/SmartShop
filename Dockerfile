#  Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY ./src ./src
RUN mvn clean package -DskipTests

#  Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app

# copy JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# expose default Spring Boot port
EXPOSE 8080

# run app
ENTRYPOINT ["java","-jar","app.jar"]
