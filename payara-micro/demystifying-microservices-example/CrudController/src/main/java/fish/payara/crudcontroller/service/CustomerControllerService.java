package fish.payara.crudcontroller.service;

import fish.payara.crudcontroller.dto.Customer;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import fish.payara.crudcontroller.restclient.CustomerPersistenceClient;

@Path("/customercontroller")
@RequestScoped
public class CustomerControllerService {

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
    public Response addCustomer(String customerJson) throws URISyntaxException {
        LOG.log(Level.INFO, String.format("addCustomer() invoked with argument %s", customerJson));

        customerPersistenceClient = RestClientBuilder.newBuilder()
                .baseUri(new URI("http://localhost:8280/CrudPersistence"))
                .build(CustomerPersistenceClient.class);

        Response response = null;
        Response persistenceServiceResponse;

        try {

            Customer customer = jsonToCustomer(customerJson);
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

    private Customer jsonToCustomer(String customerJson) {
        // [{"name":"salutation","value":"Miss"},{"name":"firstName","value":"Jillian"},{"name":"middleName","value":""},{"name":"lastName","value":"Harper"}]
        Customer customer = new Customer();

        Jsonb jsonb = JsonbBuilder.create();

        customer = jsonb.fromJson(customerJson, Customer.class);

        return customer;
    }

}
