# Base docker image
# To config a docker see links below
# https://www.baeldung.com/dockerizing-spring-boot-application
# https://mshaeri.com/blog/restful-spring-boot-application-swagger-mysql-docker-a-real-world-example/
FROM openjdk:19
ADD target/supermarket-0.0.1-SNAPSHOT.jar springboot-docker-supermarket.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "springboot-docker-supermarket.jar"]

