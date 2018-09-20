package webinar;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * REST Web Service
 *
 * @author omihalyi
 */
@Path("/hello")
@Produces(MediaType.TEXT_PLAIN)
@RequestScoped
public class HelloResource {

    @GET
    @Path("{greeting}")
    public String getGreeting(@PathParam("greeting") String greeting) {
        return greeting + ", everybody!";
    }

    @GET
    @Path("failing")
    public String getfailing() {
        throw new RuntimeException("Always fails");
    }
}
