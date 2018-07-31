package fish.payara.examples.microprofile.restclient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("clientAsync")
@RequestScoped
public class ClientResourceAsync {
    @Inject
    @RestClient
    private HelloService helloService;
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public CompletionStage<String> helloWorld() {
        return helloService.helloAsync("World (Async)")
                .thenApply(String::toUpperCase);
    }

    @Path("programmatic")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public CompletionStage<String> helloWorldProgrammatic() throws URISyntaxException {
        HelloService remoteApi = RestClientBuilder.newBuilder()
            .baseUri(new URI("http://localhost:8080/rest-client"))
            .build(HelloService.class);
        return remoteApi.helloAsync("Programmer (Async)")
                .thenApply(String::toUpperCase);
    }

}