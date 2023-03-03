# Base docker image
# To config a docker see links below
# https://www.baeldung.com/dockerizing-spring-boot-application
# https://mshaeri.com/blog/restful-spring-boot-application-swagger-mysql-docker-a-real-world-example/
# https://ilkerguldali.medium.com/1-4-lets-create-a-spring-boot-app-with-mysql-docker-docker-compose-8acaee3a2c4d

#Maven build
FROM maven:3.9.0
COPY pom.xml /app/
COPY src /app/src
RUN --mount=type=cache,target=/root/.m2 mvn -f /app/pom.xml clean package -DskipTests

#Run
FROM openjdk:19
ADD target/supermarket-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

