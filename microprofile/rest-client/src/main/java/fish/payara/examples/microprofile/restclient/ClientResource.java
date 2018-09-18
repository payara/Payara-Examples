package fish.payara.examples.microprofile.restclient;

import java.net.URI;
import java.net.URISyntaxException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("client")
@RequestScoped
public class ClientResource {
    @Inject
    @RestClient
    private HelloService helloService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorld() {
        return helloService.hello("World");
    }

    @Path("programmatic")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorldProgrammatic() throws URISyntaxException {
        HelloService remoteApi = RestClientBuilder.newBuilder()
            .baseUri(new URI("http://localhost:8080/rest-client"))
            .build(HelloService.class);
        return remoteApi.hello("Programmer");
    }

}