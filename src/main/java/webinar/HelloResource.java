package webinar;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import org.eclipse.microprofile.opentracing.Traced;

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
    @Traced(operationName = "greeting")
    public String getGreeting(@PathParam("greeting") String greeting) {
        return greeting + ", everybody!";
    }
}
