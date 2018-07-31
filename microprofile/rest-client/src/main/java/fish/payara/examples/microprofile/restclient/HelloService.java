package fish.payara.examples.microprofile.restclient;

import java.util.concurrent.CompletionStage;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author Ondrej Mihalyi
 */
@Path("/api/hello")
@Produces(MediaType.TEXT_PLAIN)  // Produces and Consumes for all methods, the can also be on a specific method
@Consumes(MediaType.TEXT_PLAIN)
@RegisterRestClient  // Required to enable injection of this interface
@RequestScoped // This is optional, @Dependent scope is the default since MicroProfile REST Client version 1.1
  // A scope annotation like RequestScoped was required to enable injection in MicroProfile REST Client version 1.0
public interface HelloService {
    @Path("{name}")
    @GET
    String hello(@PathParam("name") String name);
    
    @Path("{name}")
    @GET
    CompletionStage<String> helloAsync(@PathParam("name") String name);
}

