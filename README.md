# supermarket-spring
This is a project to study framework spring. I'll create a supermarket system with APIRestful. 

The technologies used for the development of this project were:
- [Ubuntu 22.04](https://ubuntu.com/download)
- [Java 19](https://www.oracle.com/br/java/technologies/downloads/)
- [Spring boot 3.0.2](https://spring.io/projects/spring-boot)
- [Apache Maven 3.9.0](https://maven.apache.org/download.cgi)
- [MySQL 8.0.32](https://dev.mysql.com/downloads/installer/)
- [Docker 23.0.1](https://docs.docker.com/desktop/install/ubuntu/)

## Database config
I used MySQL to manage our database. So we need configure account and password to our project work.
<br><br>
I'll create from root in MySQL a <b>user</b>`supermarket` with a <b>password</b> `SuperMarket123#`.
<br>We can change user/pass in file: `application.properties` from `src/main/resources/application.properties`

1. `CREATE USER 'supermarket' IDENTIFIED BY 'SuperMarket123#';`
2. To list all users: `SELECT USER FROM mysql.user;`

We need grant all privileges to created user to make changes in our database.

1. <code> GRANT ALL PRIVILEGES ON `supermarket`.* TO `supermarket`@`%`; </code>
2. To list all grants from supermarket: `SHOW GRANTS FOR 'supermarket';`

**_Warning_**: the best practice to MySQL accounts it's grants just basic permissions. But for this project, only for the practice project, I grant all privileges to this account.

## Docker
1. We need package first `mvn package`.
2. To make changes in `application.properties` just modify `docker-compose.yml` with you modifications.
3. Make the docker with `sudo docker-compose up -d --build`