//
// Copyright (c) 2022 Payara Foundation and/or its affiliates.
//

# Payara Embedded integration with Arquillian

The example demonstrates how to create a test suite that runs the tests inside various Payara Platform distributions using Arquillian and the Payara Embedded Arquillian Container.

You'll find the following in this project:

* Arquillian set up in [pom.xml](pom.xml) using `arquillian-bom`
* Arquillian set up in [pom.xml](pom.xml) for JUnit4 using `arquillian-junit-container`
* Profiles that configure Arquillian to run tests using various Payara Embedded containers in [pom.xml](pom.xml)
* Arquillian container configuration set up using the [arquillian.xml](src/test/resources/arquillian.xml) file. The configuration for a particular Arquilian container in `arquillian.xml` 
is enabled using `arquillian.launch` system property for the suirefire maven plugin in [pom.xml](pom.xml). 
Each maven profile enables a Arquillian configuration.
* The test case in [PersonDaoTest.java](src/test/java/fish/payara/examples/dao/PersonDaoTest.java) prepares 
a deployment for Arquillian and executes the test inside one of the Payara Arquillian containers

## Build and run the example

This is a Maven project. You'll need Maven installed to build it.

### Build all required projects

Before running this project, you also need to build the required parent projects in this repository.

Go to the root directory of this repository and execute

```
mvn --also-make --projects ecosystem/arquillian-example install
``` 

to build all required projects.

Or simply execute the following to build all projects:

```
mvn install
```


### Run the default profile

To build and run the example test using the default profile (that uses Payara Embedded):

```
mvn test
```

### Run tests using Payara Embedded

```
mvn -Ppayara-server-embedded test
```

You can run it also with `mvn test` because tis is the default configuration.

### Run tests using an existing Payara Server installation (Managed)

```
mvn -Ppayara-server-managed test
```

Payara Server is automatically installed and started (Managed). The Maven Dependency plugin 
is used to download and unpack Payara Server from Maven repository, Arquillian managed container 
starts it before exusting the tests and then stops it.

### Run tests on a running Payara Server (Remote)

Start Payara Server on local computer and then run 

```
mvn -Ppayara-server-remote test
```

This configuration runs tests against the Payara Server that must be already running on localhost. 
To run the tests on a remote Payara Server, adjust the payara-server-remote configuration in the 
[arquillian.xml](src/test/resources/arquillian.xml) file.
