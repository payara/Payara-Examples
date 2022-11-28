package fish.payara.examples.gradle;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/")
public class TestResource {

    @GET
    public String helloWorld() {
        return "Hello World!";
    }

}