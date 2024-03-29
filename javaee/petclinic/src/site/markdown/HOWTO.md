# Howto Reminder

## github
* [Issues](https://github.com/BloodMoneyApp/bloodmoney/issues)
* [Projects](https://github.com/BloodMoneyApp/bloodmoney/projects)
* [Milestones](https://github.com/BloodMoneyApp/bloodmoney/milestones)
* [Releases](https://github.com/BloodMoneyApp/bloodmoney/releases)

## Development
* [Changelog](docs/RELEASES.md)
* [Todo](docs/TODO.md)
* [Howto](docs/HOWTO.md)


## 3rd Party Software 
### Howto
* [Spring dependency-management-plugin](https://docs.spring.io/dependency-management-plugin/docs/current/reference/html/)
* [Example on github: heroku and Gradle](https://github.com/heroku/gradle-getting-started/blob/master/build.gradle)
* [Howto: Using lombok](https://projectlombok.org/setup/overview)
* [Howto: Gradle Docker Compose](https://bmuschko.com/blog/gradle-docker-compose/)

### Documentation
* [spring-boot](https://spring.io/projects/spring-boot)
* [spring-data-jpa](https://spring.io/projects/spring-data-jpa)
* [spring-framework](https://spring.io/projects/spring-framework)
* [spring-framework Data Access](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/data-access.html)
* [spring-framework WebMVC](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html)
* [spring-boot reference](https://docs.spring.io/spring-boot/docs/2.2.5.RELEASE/reference/html/)
* [spring-boot appendix-application-properties](https://docs.spring.io/spring-boot/docs/2.2.5.RELEASE/reference/html/appendix-application-properties.html#common-application-properties)
* [spring-boot api](https://docs.spring.io/spring-boot/docs/2.2.5.RELEASE/api/)
* [spring-data-jpa](https://docs.spring.io/spring-data/jpa/docs/2.2.5.RELEASE/reference/html/#reference)

### Gradle Plugins
* [org.springframework.boot](https://plugins.gradle.org/plugin/org.springframework.boot)
* [org.springframework.cloud.contract](https://plugins.gradle.org/plugin/org.springframework.cloud.contract)
* [io.spring.dependency-management](https://plugins.gradle.org/plugin/io.spring.dependency-management)
* [org.asciidoctor.jvm.convert](https://plugins.gradle.org/plugin/org.asciidoctor.jvm.convert)
* [com.avast.gradle.docker-compose](https://plugins.gradle.org/plugin/com.avast.gradle.docker-compose)
* [org.liquibase.gradle](https://plugins.gradle.org/plugin/org.liquibase.gradle)

### Github Repos
* [spring-cloud-contract](https://github.com/spring-cloud/spring-cloud-contract)
* [dependency-management-plugin](https://github.com/spring-gradle-plugins/dependency-management-plugin)
* [asciidoctor-gradle-plugin](https://github.com/asciidoctor/asciidoctor-gradle-plugin)
* [gradle-docker-compose-plugin](https://github.com/avast/gradle-docker-compose-plugin)
* [liquibase-gradle-plugin](https://github.com/liquibase/liquibase-gradle-plugin)

## Database and JPA
### DB Datatypes
* [H2 Datatypes](http://www.h2database.com/html/datatypes.html)
* [PostgreSQL Datatypes](https://www.postgresql.org/docs/11/datatype.html)
* LocalDateTime and TimeZone: TODO

### UUID and Optimistic Locking
* UUID: TODO

### Database Schema Evolution with Spring Boot JPA
* TODO
* org.flywaydb:flyway
* org.liquibase:liquibase https://www.liquibase.org/documentation/maven/index.html
 

## Frontend with webjars

### updating webjar Versions:
* change Version in build.gradle "ext" (Line 40)
* change Version in src/main/resources/templates/layout/page.html Section head.tw-head and div.tw-footer (Lines 16, 98) 
