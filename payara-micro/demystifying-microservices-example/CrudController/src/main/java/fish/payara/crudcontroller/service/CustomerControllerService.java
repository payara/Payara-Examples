package fish.payara.crudcontroller.service;

import fish.payara.crudcontroller.dto.Customer;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import fish.payara.crudcontroller.restclient.CustomerPersistenceClient;
import javax.inject.Inject;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/customercontroller")
@RequestScoped
public class CustomerControllerService {

    @Inject
    @RestClient
    private CustomerPersistenceClient customerPersistenceClient;

    private static final Logger LOG = Logger.getLogger(CustomerControllerService.class.getName());

    @OPTIONS
    public Response options() {
        LOG.log(Level.INFO, "CustomerControllerService.options() invoked");
        return Response.ok("")
                .header("Access-Control-Allow-Origin", "http://localhost:8080")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Add a customer to the database",
            description = "Add a customer to the database based"
            + " on the JSON representation of the customer")
    public Response addCustomer(Customer customer) throws URISyntaxException {

        Response response = null;
        Response persistenceServiceResponse;

        try {

            persistenceServiceResponse = customerPersistenceClient.create(customer);

            if (persistenceServiceResponse.getStatus() == 201) {
                response = Response.ok("{}").
                        header("Access-Control-Allow-Origin", "http://localhost:8080").build();

            } else {
                response = Response.serverError().
                        header("Access-Control-Allow-Origin", "http://localhost:8080").build();
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Exception while processing request", e);
            response = Response.serverError().
                    header("Access-Control-Allow-Origin", "http://localhost:8080").build();
        }

        return response;
    }

}
