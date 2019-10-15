# Kubernetes Example

This is a microservice application with the associated Kubernetes scripts to run it.

- [Frontend](frontend/) - A Vue.js application served on port 8080 that interfaces with the backend to display results.
- [Backend](backend/) - A MicroProfile project served on port 8081 that interfaces with the name generator to return a greeting.
- [Name Generator](name-generator/) - A MicroProfile project served on port 8082 that returns a random Payara instance name.

## Quick Start

To build the artifacts, run the following:

~~~
mvn clean install
~~~

## Building Docker Images

If Docker is installed locally, you can build the images for each subproject with the following command:

~~~
mvn clean install -Pdocker
~~~

## Running the Examples Locally

To run each the entire demo without Kubernetes, make sure ports `8080`, `8081` and `8082` are open, then from 3 separate terminals go into each of the 3 subprojects and run:

~~~
mvn clean verify -Pserve
~~~