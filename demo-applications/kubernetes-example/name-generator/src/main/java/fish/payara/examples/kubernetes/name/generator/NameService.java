package fish.payara.examples.kubernetes.name.generator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import fish.payara.nucleus.hazelcast.NameGenerator;

@Path("/name")
public class NameService {

    private NameGenerator generator;

    public NameService() {
        this.generator = new NameGenerator();
    }

    @GET
    @Path("/random")
    public String getRandomName() {
        return generator.generateName();
    }

}