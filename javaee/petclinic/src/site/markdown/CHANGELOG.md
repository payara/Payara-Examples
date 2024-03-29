# Releases

## github
* [Issues](https://github.com/BloodMoneyApp/bloodmoney/issues)
* [Projects](https://github.com/BloodMoneyApp/bloodmoney/projects)
* [Milestones](https://github.com/BloodMoneyApp/bloodmoney/milestones)
* [Releases](https://github.com/BloodMoneyApp/bloodmoney/releases)

## Development Documents
* [CHANGELOG](src/site/markdown/CHANGELOG.md)
* [DEPLOYMENTS](src/site/markdown/DEPLOYMENTS.md)
* [DOCKER](src/site/markdown/DOCKER.md)
* [HELP](src/site/markdown/HELP.md)
* [HOWTO](src/site/markdown/HOWTO.md)
* [TODO](src/site/markdown/TODO.md)

## Changelog
## DRAFT (1.9 Release dd.mm.2020)
### Fixed
|  Release 1.9 | *Fixed*                             |
|--------------|-------------------------------------|
| **Project**  | **IntegrationTests and Unit-Tests** |
| **Project**  | **DatabaseSchemaEvolution**         |
| **Project**  | **REST-API**                        |
| **Project**  | **Frontend**                        |
| **Project**  | **Deployments**                     |

### Known Issues
| Release 1.9 | *Known Issues*                                                                                                                                              |
|-------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Project** | **IntegrationTests and Unit-Tests**                                                                                                                         |
| #36         | read, apply and evaluate [Spring Security for Spring Boot Integration Tests](https://www.baeldung.com/spring-security-integration-tests)                    |
| **Project** | **Database Schema Evolution**                                                                                                                               |
| #40         | add UUID Generation from Contructor and factories to Framework Automatism                                                                                   |
| #42         | apply and evaluate Flyway for DatabaseSchemaEvolution: https://flywaydb.org/                                                                                |
| #43         | apply and evaluate Liquibase for DatabaseSchemaEvolution: https://www.liquibase.org/                                                                        |
| #44         | read, apply and evaluate [Use Liquibase to Safely Evolve Your Database Schema](https://www.baeldung.com/liquibase-refactor-schema-of-java-app)              |
| #45         | read [Liquibase – das Tool für agiles Database Deployment 20.10.2014](https://blog.orbit.de/2014/10/20/liquibase-das-tool-fuer-agiles-database-deployment/) |
| #46         | read [Getting Started with Liquibase](https://www.liquibase.org/get_started/index.html)                                                                     |
| **Project** | **REST-API**                                                                                                                                                |
| #58         | read [Introduction to Spring Method Security](https://www.baeldung.com/spring-security-method-security)                                                     |
| **Project** | **Frontend**                                                                                                                                                |
| #50         | add bootwatch for bootstrap                                                                                                                                 |
| **Project** | **Deployments**                                                                                                                                             |
| #51         | prepare DatabaseSchemaEvolution for Deployment                                                                                                              |
| #52         | prepare REST-API for Deployment                                                                                                                             |
| #53         | prepare Frontend for Deployments                                                                                                                            |
| #54         | prepare IntegrationTests and Unit-Tests for Deployments                                                                                                     |
| #55         | Deploy Candidate to Heroku                                                                                                                                  |
| #56         | Test the Deployment                                                                                                                                         |
| #57         | make Release for Deployment                                                                                                                                 |

## 1.8 Release 08.03.2020
### Fixed
| Release 1.8 | *Fixed*                                                                                                                                        |
|-------------|------------------------------------------------------------------------------------------------------------------------------------------------|
| **Project** | **IntegrationTests and Unit-Tests**                                                                                                            |
| #30         | use Lombok in Test Classes                                                                                                                     |
| #31         | use Logging in Test Classes                                                                                                                    |
| #32         | Test a SpringMVC Controller                                                                                                                    |
| #33         | Test a Rest Controller                                                                                                                         |
| #34         | Test a SpringMVC Controller with Spring Security                                                                                               |
| #35         | Test a Rest Controller with Spring Security                                                                                                    |
| #37         | read, apply and evaluate [Testing Spring Boot Spring Security](https://www.codeflow.site/de/article/spring-security-integration-tests)         |
| #38         | read [spring-boot-setup-security-for-testing](https://stackoverflow.com/questions/23335200/spring-boot-setup-security-for-testing)             |
| **Project** | **DatabaseSchemaEvolution**                                                                                                                    |
| #39         | add UUID Generation to Constructor and factories                                                                                               |
| #41         | make Flyway and H2 Versions compatible                                                                                                         |
| **Project** | **REST-API**                                                                                                                                   |
| #25         | read [JAX-RS is just an API!](https://www.baeldung.com/jax-rs-spec-and-implementations)                                                        |
| #26         | read [Spring Boot JAX-RS Example March 29th, 2019](https://examples.javacodegeeks.com/enterprise-java/spring/boot/spring-boot-jax-rs-example/) |
| #27         | read [AX-RS/Jersey and Spring REST](https://learnjava.co.in/jax-rs-vs-spring-rest/)                                                            |
| #28         | read [Let's Compare: JAX-RS vs Spring for REST Endpoints](https://developer.okta.com/blog/2017/08/09/jax-rs-vs-spring-rest-endpoints)          |
| #29         | read, apply and evaluate [REST API with Jersey and Spring](https://www.baeldung.com/jersey-rest-api-with-spring)                               |
| #47         | Add a REST Controller Resource                                                                                                                 |
| #48         | Add a REST Controller Resource for org.woehlke.bloodmoney.measurements.BloodPressureMeasurementEntity                                          |
| **Project** | **Frontend**                                                                                                                                   |
| #49         | remove cookieconsent2/3.1.0/cookieconsent.min.js                                                                                               |
### Known Issues
| Release 1.8 | *Known Issues*                                                                                                                                              |
|-------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Project** | **IntegrationTests and Unit-Tests**                                                                                                                         |
| #36         | read, apply and evaluate [Spring Security for Spring Boot Integration Tests](https://www.baeldung.com/spring-security-integration-tests)                    |
| **Project** | **Database Schema Evolution**                                                                                                                               |
| #40         | add UUID Generation from Contructor and factories to Framework Automatism                                                                                   |
| #42         | apply and evaluate Flyway for DatabaseSchemaEvolution: https://flywaydb.org/                                                                                |
| #43         | apply and evaluate Liquibase for DatabaseSchemaEvolution: https://www.liquibase.org/                                                                        |
| #44         | read, apply and evaluate [Use Liquibase to Safely Evolve Your Database Schema](https://www.baeldung.com/liquibase-refactor-schema-of-java-app)              |
| #45         | read [Liquibase – das Tool für agiles Database Deployment 20.10.2014](https://blog.orbit.de/2014/10/20/liquibase-das-tool-fuer-agiles-database-deployment/) |
| #46         | read [Getting Started with Liquibase](https://www.liquibase.org/get_started/index.html)                                                                     |
| **Project** | **REST-API**                                                                                                                                                |
| #58         | read [Introduction to Spring Method Security](https://www.baeldung.com/spring-security-method-security)                                                     |
| **Project** | **Frontend**                                                                                                                                                |
| #50         | add bootwatch for bootstrap                                                                                                                                 |
| **Project** | **Deployments**                                                                                                                                             |
| #51         | prepare DatabaseSchemaEvolution for Deployment                                                                                                              |
| #52         | prepare REST-API for Deployment                                                                                                                             |
| #53         | prepare Frontend for Deployments                                                                                                                            |
| #54         | prepare IntegrationTests and Unit-Tests for Deployments                                                                                                     |
| #55         | Deploy Candidate to Heroku                                                                                                                                  |
| #56         | Test the Deployment                                                                                                                                         |
| #57         | make Release for Deployment                                                                                                                                 |

## 1.7 Release 05.03.2020 
## 1.6 Release 05.03.2020
## 1.5 Release 05.03.2020
## 1.4 Release 05.03.2020
## 1.3 Release 05.03.2020
## 1.2
## 1.1
## 1.0 https://bloodmoney.herokuapp.com/


## next commit:
* fixed # 36 apply Spring Security for Spring Boot Integration Tests

## 1.8.3
* Fixed #53
* Fixed #55
* Fixed #56
* Fixed #57 
* Fixed #59
* Fixed #60

## 1.8.4
* Fixed #24

## 1.8.5
* fixed #67 Update to Spring-Boot 2.3.3 
* fixed #68 Update to Spring Data Bom Neumann-SR3 for Spring-Boot 2.3.3
* fixed #69 Update to Spring Framework for Spring-Boot 2.3.3
* fixed #70 Update to Spring Security for Spring-Boot 2.3.3

## 1.8.6
* merge branches
* fixed #78 Release 1.8.6

## 1.8.7
* release plugin

## 1.8.8
* fixed #79 merge branches
* fixed #80 release plugin 
* fixed #75 SLF4J: Class path contains multiple SLF4J bindings
* fixed #50 add bootwatch for bootstrap

## 1.8.9
* fixed #81 mvnw docker-compose:up 
* fixed #84 update webjars

## 1.8.10
* Merge #86
 
## 1.8.11
* fixed #87 update spring-boot to 2.3.4
* fixed #88 update spring-data-releasetrain to Neumann-SR4
* fixed #89 update spring-session-bom to Dragonfruit-SR1
* fixed #90 update spring-security-bom
* fixed #91 update spring-framework-bom
* fixed #92 update spring-integration-bom
* fixed #93 update webjars
* fixed #94 add github actions for CI 

## 1.8.12
* fixed #113 check dependency: spring-boot-admin-starter-client
* fixed #114 check dependency: spring-boot-admin-starter-server
* fixed #95 check dependency: thymeleaf-testing
* fixed #96 check dependency: thymeleaf-spring-data-dialect
* fixed #119 check dependency: thymeleaf
* fixed #120 check dependency: thymeleaf-spring5
* fixed #121 check dependency: thymeleaf-extras-java8time
* fixed #122 check dependency: thymeleaf-extras-springsecurity5
* fixed #123 check dependency: thymeleaf-extras-data-attribute
* fixed #124 check dependency: thymeleaf-spring-data-dialect
* fixed #107 check dependency: spring-boot-starter-mustache
* fixed #101 check dependency: spring-boot-devtools
* fixed #102 check dependency: spring-boot-configuration-processor
* fixed #103 check dependency: spring-boot-properties-migrator
* fixed #137 Webapp shall send Emails
* fixed #109 check dependency: spring-boot-starter-mail
* fixed #48 Add a REST Controller Resource for org.woehlke.bloodmoney.measurements.BloodPressureMeasurement
* fixed #106 check dependency: spring-boot-starter-jersey
* fixed #111 check dependency: spring-boot-starter-json
* fixed #128 check dependency: jaxb-api
* fixed #125 check dependency: opencsv
* fixed #104 check dependency: spring-boot-starter-data-rest
* fixed #105 check dependency: spring-boot-starter-hateoas
* fixed #117 check dependency: spring-data-rest-hal-explore
* fixed #118 check dependency: spring-data-rest-hal-explore 
* fixed #130 check dependency: spring-restdocs-mockmvc
* fixed #108 check dependency: spring-boot-starter-web-services
* fixed #110 check dependency: spring-boot-starter-cache
* fixed #126 check dependency: jsoup
* fixed #115 check dependency: spring-session-core
* fixed #116 check dependency: spring-session-jdbc
* fixed #127 check dependency: hibernate-core
* fixed #129 check dependency: postgresql
* fixed #112 check dependency: spring-boot-starter-test

## 1.8.13
* fixed #131 maven-changes-plugin is broken
* fixed #147 REST button.export.xml
* fixed #148 REST button.export.json
* fixed #149 http://localhost:8080/rest/measurement/all.json JSON broken
* fixed #150 http://localhost:8080/rest/measurement/all.xml XML broken

## 1.8.14
* fixed #154 try out java15

## 1.8.15
* issue #154 try out java15
* issue #17 add Security config for API
* issue #151 /rest/measurement/all.xml HTTP Status 406 – Not Acceptable
* issue #97 check dependency: jsoup
* issue #99 check dependency: asm
* issue #100 check dependency: attoparser
* issue #98 check dependency: opencsv
* issue #82 implement void updateLastLoginTimestamp(UserAccountBean user);


