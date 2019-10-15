# Kubernetes Frontend Example

This is the frontend for the Kubernetes example. it is written with Vue.js, but can be compiled with Maven.

## Quick Start

To build the artifacts, run the following:

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
mvn clean test -Pserve
~~~

## Direct NPM Goals
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Run your tests
```
npm run test
```

### Lints and fixes files
```
npm run lint
```