package fish.payara.examples.microprofileonmicro.restclient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author Andrew Pielage <andrew.pielage@payara.fish>
 */
@Path("/RestClient")
@RegisterRestClient(baseUri = "http://localhost:8080")
@ApplicationScoped
public interface RestClientExample {
    
    @Path("/Hello")
    @GET
    String hello();
    
}
