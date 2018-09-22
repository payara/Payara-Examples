# Request Tracing @Traced Example
An example demonstrating a very simple use of the `@Traced` annotation. The `@Traced` annotation allows you to trace the entry and exit of CDI requests.  
The example has a servlet redirect to a second servlet, that utilises an EJB, that itself calls a `RequestScoped` Bean annotated with `@Traced`.

## Instructions
To run this example, start a Payara Server, `v5.181` is the supported version, and enable the Request Tracing service with a threshold of 3 seconds.
