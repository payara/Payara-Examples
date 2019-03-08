package fish.payara.crudcontroller.restclient;

import fish.payara.crudcontroller.dto.Customer;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/webresources/customerpersistence")
@RegisterRestClient
public interface CustomerPersistenceClient {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Customer customer);

}
