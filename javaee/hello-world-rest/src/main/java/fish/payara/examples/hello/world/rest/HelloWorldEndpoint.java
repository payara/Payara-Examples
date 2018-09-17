package fish.payara.examples.hello.world.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import fish.payara.examples.hello.world.rest.filter.annotations.TracedEndpoint;

@Path("/hello")
public class HelloWorldEndpoint {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @TracedEndpoint
    public String helloWorld() {
        return "Hello World!";
    }
}
