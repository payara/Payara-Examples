package fish.payara.crudcontroller.service;

import fish.payara.crudcontroller.dto.Customer;
import fish.payara.crudcontroller.restclient.CustomerPersistenceClient;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/customercontroller")
public class CustomerControllerService {

    private static final Logger LOG = Logger.getLogger(CustomerControllerService.class.getName());

    public CustomerControllerService() {
    }

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
    public Response addCustomer(Customer customer) {

        Response response;
        Response persistenceServiceResponse;

        try {

            CustomerPersistenceClient client = new CustomerPersistenceClient();
            persistenceServiceResponse = client.create(customer);
            client.close();

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
