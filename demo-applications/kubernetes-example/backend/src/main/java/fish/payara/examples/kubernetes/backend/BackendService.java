package fish.payara.examples.kubernetes.backend;

import java.net.URI;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

@Path("/name")
@RequestScoped
public class BackendService {

    private NameService nameService;

    @Inject
    public void initNameService(@ConfigProperty(name = "name.service.url") String nameServiceUrl) {
        nameService = RestClientBuilder
                .newBuilder()
                .baseUri(URI.create(nameServiceUrl))
                .build(NameService.class);
    }

    @GET
    @Path("/random")
    public String getRandomName() {
        return nameService.getRandomName();
    }

}