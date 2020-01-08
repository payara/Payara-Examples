# Zipkin Tracing Wrapper Library

This is a project to demonstrate how one might set up a wrapper for Zipkin Tracer to use on Payara 5.194 and above as an alternative implementation of Opentracing.

## Building

Simply run `mvn clean install` to build this wrapper implementation.

## Using with Payara Server

IMPORTANT: Deploying this project as an application *DOES NOT WORK*
The resulting JAR file from building this example project should be added to Payara Server as a library

Once the server is running, add the JAR as a library with the asadmin command `add-library zipkin-tracer-lib-1.0-jar-with-dependencies.jar` as per the server documentation.

Tracing must be enabled with `set-requesttracing-configuration --enabled true --dynamic true`

After executing these steps, navigate to the Zipkin UI at http://localhost:9411/ (as configured by default in the ZipkinTracerWrapper class) and you will be able to retrieve traces from Payara Server. If your Zipkin instance is configured to a different URL, simply change the URL in ZipkinTracerWrapper to match your destination and rebuild the project.
