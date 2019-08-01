package fish.payara.examples.gradle;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class TestResource {

    @GET
    public String helloWorld() {
        return "Hello World!";
    }

}