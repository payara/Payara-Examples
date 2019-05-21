# Kubernetes Backend Example

This is the name-generator service for the Kubernetes example. It is a MicroProfile example JAX-RS.

## Quick Start

To build the artifacts, run:

~~~
mvn clean install
~~~

## Building Docker Images

If Docker is installed locally, you can build the image with the following command:

~~~
mvn clean install -Pdocker
~~~

The docker image will host the files statically on port 80.

## Running the Examples Locally

To run each the example without Kubernetes, run:

~~~
mvn clean integration-test -Pserve
~~~