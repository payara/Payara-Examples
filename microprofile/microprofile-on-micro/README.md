# MicroProfile on Micro

This is an example demonstrating basic usage of each of the MicroProfile specs 
found in Payara Micro 5.193.

It was created for Payara Micro 5.193, and written using NetBeans and the NetBeans Payara Micro plugin to demonstrate the
redeployment features.

## Usage
The application, once built and deployed, exposes a Servlet and a number of JAX-RS endpoints,
each one demonstrating basic usage of the 8 MicroProfile specs: Config, Fault Tolerance, Health, JWT-Auth, Metrics, 
OpenAPI, OpenTracing, and Rest Client.

The endpoints, and a brief description of what they do, are as follows:

* /MicroProfileOnMicro-1.0-SNAPSHOT/ConfigServlet - A servlet that gets and returns a number of MicroProfile config properties
* /MicroProfileOnMicro-1.0-SNAPSHOT/FaultTolerance/Bulkhead - this method sleeps for 5 seconds and has a bulkhead of three, preventing more than three invocations of this method from occurring at once
* /MicroProfileOnMicro-1.0-SNAPSHOT/FaultTolerance/AsyncBulkhead - this method sleeps for 10 seconds and has an asynchronous bulkhead, preventing more than 5 invocations of this method from occurring at once (three executing, two queueing) 
* /MicroProfileOnMicro-1.0-SNAPSHOT/FaultTolerance/CircuitBreaker - this method has a 50% chance of throwing a RuntimeException and has a circuitbreaker, breaking the circuit and temporarily preventing invocations of this method from occurring if twoo many failures happen within a rolling window
* /MicroProfileOnMicro-1.0-SNAPSHOT/FaultTolerance/Fallback - this method has a 50% chance of throwing a RuntimeException, falling back to an alternative method that returns a String when it fails
* /MicroProfileOnMicro-1.0-SNAPSHOT/FaultTolerance/Retry - this method has a 25% chance of throwing an IOException, and a 25% chance of throwing a RuntimeException. For the former, the method will retry once, for the latter, it will fallback to a method that returns a string.
* /MicroProfileOnMicro-1.0-SNAPSHOT/FaultTolerance/Timeout - this method sleeps for up to 5 seconds and returns how long it slept for, timing out and falling back on returning MAX_INT if it sleeps for more than 2.5 seconds.
* /health - runs and collects the outcome from any defined healthchecks, one of which will fail 50% of the time.
* /MicroProfileOnMicro-1.0-SNAPSHOT/JwtAuth - an unprotected method, simply returning a string
* /MicroProfileOnMicro-1.0-SNAPSHOT/JwtAuth/Access - a method that generates an access token for the "/Protected" endpoint and access it
* //MicroProfileOnMicro-1.0-SNAPSHOT/JwtAuth/Protected - a protected method that requires a user to be in "payara" role to access
* /MicroProfileOnMicro-1.0-SNAPSHOT/Metrics/Counted - returns how frequently this method has been accessed
* /MicroProfileOnMicro-1.0-SNAPSHOT/Metrics/InjectedCounted - returns how frequently an injected counter has been accessed
* /MicroProfileOnMicro-1.0-SNAPSHOT/Metrics/Gauge - returns a value between 0 and 100 to demonstrate a gauge (in this case, percentage)
* /MicroProfileOnMicro-1.0-SNAPSHOT/Metrics/Metered - returns the one minute rate for this method
* /MicroProfileOnMicro-1.0-SNAPSHOT/Metrics/ConcurrentGauge - sleeps for 5 seconds, before returning how many methods are concurrently executing on this method
* /MicroProfileOnMicro-1.0-SNAPSHOT/Metrics/Timed - sleeps for up to 5 seconds, and returns how long it slept for.
* /MicroProfileOnMicro-1.0-SNAPSHOT/OpenApi/Wobbly - simply returns some text, this method is used to demonstrate how you can annotate your methods with meta-information for viewing via the /openapi endpoint
* /MicroProfileOnMicro-1.0-SNAPSHOT/OpenApi/Tiddles/{Toddles} - simply returns some text, this method is used to demonstrate how you can annotate your methods with meta-information for viewing via the /openapi endpoint
* /MicroProfileOnMicro-1.0-SNAPSHOT/OpenTracing/ - calls the "/Hello" endpoint, used for showing chained traces
* /MicroProfileOnMicro-1.0-SNAPSHOT/OpenTracing/Hello - grabs the active span and adds a tag, before returning a string
* /MicroProfileOnMicro-1.0-SNAPSHOT/OpenTracing/Thirsty - calls a traced method on an injected bean
* /MicroProfileOnMicro-1.0-SNAPSHOT/OpenTracing/Hungry - asynchronously calls a traced method on an injected bean and returns a string, to demonstrate cross-thread tracing
* /MicroProfileOnMicro-1.0-SNAPSHOT/RestClient/SayHello - calls the "hello" method on an injected rest client (which calls the "Hello" endpoint) 
* /MicroProfileOnMicro-1.0-SNAPSHOT/RestClient/Hello - simply returns a string, used to demonstrate usage of the rest client
* /MicroProfileOnMicro-1.0-SNAPSHOT/RestClient/Greetings - creates a new Rest Client and calls the "hello" method.