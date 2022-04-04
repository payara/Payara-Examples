# gRPC example with an EJB

This is a project to demonstrate how to use gRPC with an EJB on Payara Server. The gRPC service is setup to echo the client message, which in this example is always "Hello World".

## Building
Run `mvn clean install` from the grpc-ejb-server directory to build the server side of this example. The client side of this demo does not need compiling.

## Using with Payara Server

To use the gRPC service within Payara Server you will first need to build the Payara gRPC module. Clone https://github.com/payara/gRPC, build the module with `mvn clean install` and drop the JAR from the target directory into the `${PAYARA_HOME}/glassfish/modules` directory of your Payara Server Installation.

Restart the domain and any running instances to enable the module.

## Using the gRPC Service

### Server Side gRPC Service
Now Payara is configured to support gRPC, deploy the `grpc-ejb-server` war  to Payara to configure the server side gRPC service.

### Client Side gRPC Service

To execute the Client Side gRPC call run `mvn compile exec:java "-Dexec.mainClass=fish.payara.samples.grpc.GrpcClient"` from the `Payara-Examples\grpc\grpc-ejb\grpc-ejb-client` directory.

Now in the client log you will see the client message, in this case that's always "Hello World" and the number of times you have called the gRPC service, if you repeat the client side call you will see this number increment.