package webinar;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 * REST Web Service
 *
 * @author omihalyi
 */
@Path("/")
@Produces(MediaType.TEXT_PLAIN)
@RequestScoped
public class RootResource {

    @Inject
    @RestClient
    HelloClient helloClient;
    
    @GET
    public String hello() {
        return helloClient.getGreeting("Hello");
    }
}
