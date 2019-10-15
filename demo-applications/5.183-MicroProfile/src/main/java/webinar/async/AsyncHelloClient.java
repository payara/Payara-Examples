package webinar.async;

import java.util.concurrent.CompletionStage;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author omihalyi
 */
@Path("/hello")
@Produces(MediaType.TEXT_PLAIN)
@RegisterRestClient
@RequestScoped
public interface AsyncHelloClient {

    @GET
    @Path("{greeting}")
    CompletionStage<String> getGreeting(@PathParam(value = "greeting") String greeting);
    
}
