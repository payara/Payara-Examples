# Example of using Microprofile REST Client API

This example shows how to use the Microprofile REST Client API to access a remote Hello REST service using an interface ([`HelloService.java`](src/main/java/fish/payara/examples/microprofile/restclient/HelloService.java)) annotated with JAX-RS annotations.

NOTE: MicroProfile REST Client API v1.1 is provided by Payara Plaform (Payara Server and Payara Micro) since version 5.182. It's a part of MicroProfile since version 1.4 and 2.0.

## Run the application

1. Build with:

```
mvn -Puberjar install
```
2. Run the app with the following: `java -jar rest-client-microbundle.jar`

* You can also deploy rest-client.war on Payara Server on context root `rest-client`, or run with Payara Micro: `java -jar payara-micro.jar target/rest-client.war`

3. Access the Client REST resource with [http://localhost:8080/rest-client/api/client](http://localhost:8080/rest-client/api/client)

* the "client" resource uses an injected instance of `HelloService`
* [http://localhost:8080/rest-client/api/client/programmatic](http://localhost:8080/rest-client/api/client/programmatic) resource uses a programmatically built instance of `HelloService`

------------------------------

* [http://localhost:8080/rest-client/api/clientAsync](http://localhost:8080/rest-client/api/clientAsync) resource uses an injected instance of `HelloService` and calls it asynchronously
* [http://localhost:8080/rest-client/api/clientAsync/programmatic](http://localhost:8080/rest-client/api/clientAsync/programmatic) resource uses a programmatically built instance of `HelloService` and calls it asynchronously

## Introduction

The interface `HelloService` is used in the [`ClientResource.java`](src/main/java/fish/payara/examples/microprofile/restclient/ClientResource.java) to call a remote Hello service. The remote service is provided by the [HelloRemoteResource.java](src/main/java/fish/payara/examples/microprofile/restclient/remote/HelloRemoteResource.java) resource on localhost but could also be provided by a separate application running remotely. 

If you compare the source code of `HelloService` and `HelloRemoteResource`, you'll see that they both define the same methods and use the same JAX-RS annotations. They are just interpreted in the opposite direction to marshal a Java call into a REST request.

## Declaring a REST client interface

The HelloService uses `@Path` to define the path to append to the base client URI. The path is usually static and doesn't change, while the base URI depends factors like HTTP protocol, domain, port and application context root of the remote service. 

The `@Path` annotation can be used also on a method to append path the base URL. It may include path variables, which can be referenced with `@PathParam` in method arguments as in JAX-RS server resources.

`@Produces` and `@Consumes` can be used on class level and on method level to define supported content types in the same way as they can be use in JAX-RS server resources. 

Exactly one request method annotation like `@GET`, `@POST`, etc. need to be placed on each method to specify the HTTP method for a client REST request.

### Enable injection of a REST client interface

The HelloService interface is annotated with the annotation `@RegisterRestClient`. This is required by the MicroProfile specification to register the interface as a REST client so that it can be simply injected as a `HelloService` implementation. The interface is also annotated with `@RequestScoped` to declare the scope of the injected bean. This was required in older versions of the REST Client API. Since version 1.1 (MicroProfile 1.4 and 2.0), it is optional, injected beans have dependent scope if no scope annotation present on the interface.

## Using a REST client interface

### Inject a REST client interface

An interface annotated with `@RegisterRestClient` can be injected in any CDI bean. You need to mark an injection point with the `@RestClient` qualifier annotation:

```java
    @Inject
    @RestClient
    private HelloService helloService;
```

Then you can simply call methods in the injected `HelloService` instance:

```java
    helloService.hello("World");
```

The full example is in [`ClientResource.java`](src/main/java/fish/payara/examples/microprofile/restclient/ClientResource.java).

The injected proxy will use a default configuration. You can modify its configuration using MicroProfile Config properties. For example, to specify the base URL for the remote service, you can specify a property `fish.payara.examples.microprofile.restclient.HelloService/mp-rest/url`. In this example, this property is specified in [`microprofile-config.properties`](/src/main/resources/META-INF/microprofile-config.properties)

The properties have names prefixed with the full class name of the REST Client interface, followed by `/mp-rest/`, followed by the name of the client property, such as `url`.

NOTE: The base URL is a required configuration. Therefore either `url` or `uri` needs to be provided using an appropriate MicroProfile Config property, otherwise injection fails.

### Access a REST client interface programmatically

It's possible to create a REST client proxy programmatically with a builder retrieved by `RestClientBuilder.newBuilder()`. This builder implements the same `javax.ws.rs.core.Configurable` interface that is implemented by JAX-RS `ClientBuilder`. You may therefore reuse the JAX-RS ClientBuilder API to configure the created MicroProfile REST Client proxy:

```java
    HelloService remoteApi = RestClientBuilder.newBuilder()
        .baseUri(new URI("http://localhost:8080/rest-client"))
        .build(HelloService.class);
```

A full example is in [`ClientResource.java`](src/main/java/fish/payara/examples/microprofile/restclient/ClientResource.java).

## Asynchronous processing with CompletionStage

MicroProfile REST Client also supports asynchronous invocation of REST services since version 1.1 (available in Payara Platform since version 5.182).

This means that methods in a REST client interface can also return `CompletionStage` instead of a direct value. In the same way, `CompletionStage` as a return type is supported in REST server resources by JAX-RS 2.1.

When a method that returns `CompletionStage` is called, it will return as soon as possible, without waiting for the REST call to complete. The returned stage is completed (either with a result or exceptionally) when the REST call is completed or timed out. The important aspect of returning `CompletionStage` is that the calling thread isn't blocked and can continute further processing. The REST call happens in the background.

The example ([`HelloService.java`](src/main/java/fish/payara/examples/microprofile/restclient/HelloService.java)) contain an asynchronous method `helloAsync`. This method returns `CompletionStage<String>` instead of `String` returned by the `hello` method.

The asynchronous method can be called in the same way as the synchronous method. The code to execute after the REST call returns should be chained using the returned `CompletionStage`:

```java
    helloService.helloAsync("World (Async)")
        .thenApply(String::toUpperCase);
```

The code chained to the `CompletionStage` will be executed in a thread managed by the container. It's possible to specify a custom executor using the programmatic API:

```java
    HelloService remoteApi = RestClientBuilder.newBuilder()
        .baseUri(new URI("http://localhost:8080/rest-client"))
        .executorService(customExecutor)
        .build(HelloService.class);
```

NOTE: If your application is running in a MicroProfile container, you should use a managed executor provided by the container. In Java EE containers, a managed executor can be retrieved by injecting a ManagedExecutorService or created by an injected `ManagedThreadFactory`.

In Payara Platform, you can inject a managed executor using the following code:

```java
    @Resource(name = "<my executor name>")
    ManagedExecutorService executor;
```

Or you can create an executor with a factory:

```java
    @Resource
    ManagedThreadFactory tf;
 
    ManagedExecutorService executor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS,
       new ArrayBlockingQueue<Runnable>(10), tf);
```

A full example of the asynchronous API (with the default executor provided by the container) is in [`ClientResourceAsync.java`](src/main/java/fish/payara/examples/microprofile/restclient/ClientResourceAsync.java).
