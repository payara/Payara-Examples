package fish.payara.examples.kubernetes.backend;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/name")
public interface NameService {

    @GET
    @Path("/random")
    public String getRandomName();

}