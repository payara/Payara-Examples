package webinar;

import java.net.URI;
import java.net.URL;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.*;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

/**
 * REST Web Service
 *
 * @author omihalyi
 */
@Path("")
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

    @Inject
    @ConfigProperty(name = "webinar.HelloClient/mp-rest/url")
    URI helloServiceUrl; // converter for URI and other types with String constructor added in MicroProfile 2.0

    @GET
    @Path("prog")
    public String helloProgrammatic() {
        HelloClient helloClientProg = RestClientBuilder.newBuilder()
                .baseUri(helloServiceUrl)
                .build(HelloClient.class);
        return helloClientProg.getGreeting("You rock");
    }

    @GET
    @Path("fail")
    @Retry
    @Fallback(fallbackMethod = "handle")
    public String reactToFailures() {
        return helloClient.getfailing();
    }

    public String handle() {
        return "Client failed, this is a fallback message";
    }

}
