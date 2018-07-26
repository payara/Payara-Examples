package fish.payara.examples.microprofile.restclient.remote;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class HelloRemoteResource {

    @Path("{name}")
    @GET
    public String hello(@PathParam("name") String name) {
        return "Hello " + name + "!";
    }

}
