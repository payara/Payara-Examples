package fish.payara.examples.kubernetes.backend;

import java.net.URI;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

@Path("/greeting")
@RequestScoped
public class BackendService {

    private NameService nameService;

    @Inject
    public void initNameService(@ConfigProperty(name = "name.service.url") URI nameServiceUrl) {
        nameService = RestClientBuilder
                .newBuilder()
                .baseUri(nameServiceUrl)
                .build(NameService.class);
    }

    @GET
    public String getGreeting() {
        return "Hello " + nameService.getRandomName();
    }

}
