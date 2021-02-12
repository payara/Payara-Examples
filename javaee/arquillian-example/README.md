# Payara Embedded integration with Arquillian

The example demonstrates how to create a test suite that runs the tests inside the Payara Embedded using Arquillian and the Payara Embedded Arquillian Container.

You'll find the following in this project:

* Arquillian set up in [pom.xml](pom.xml) using `arquillian-bom`
* Arquillian set up in [pom.xml](pom.xml) for JUnit4 using `arquillian-junit-container`
* Arquillian set to run tests in Payara Embedded in [pom.xml](pom.xml) using `arquillian-payara-server-embedded` container artifact and the `payara-embedded-all` artifact with the required version of Payara Embedded
* Payara Embedded configuration set up using the [arquillian.xml](src/test/resources/arquillian.xml)` file that references [glassfish-resources.xml](src/test/resources/glassfish-resources.xml). The configuration in `arquillian.xml` is enabled using `arquillian.launch` system property for the suirefire maven plugin in [pom.xml](pom.xml)
* The test case in [PersonDaoTest.java](src/test/java/fish/payara/examples/dao/PersonDaoTest.java) prepares a deployment for Arquillian and executes the test inside Payara Embedded

## Build and run the example

This is a Maven project. To build and run the example test:

```
mvn test
```

You also need to build the required parent projects in this repository.