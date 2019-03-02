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
    public Response addCustomer(String customerJson) {
        LOG.log(Level.INFO, String.format("addCustomer() invoked with argument %s", customerJson));

        Response response;
        Response persistenceServiceResponse;

        CustomerPersistenceClient client = new CustomerPersistenceClient();
        Customer customer = jsonToCustomer(customerJson);
        persistenceServiceResponse = client.create(customer);
        client.close();

        if (persistenceServiceResponse.getStatus() == 201) {
            response = Response.ok("{}").
                    header("Access-Control-Allow-Origin", "http://localhost:8080").build();

        } else {
            response = Response.serverError().
                    header("Access-Control-Allow-Origin", "http://localhost:8080").build();
        }

        return response;
    }

    private Customer jsonToCustomer(String customerJson) {
        // [{"name":"salutation","value":"Miss"},{"name":"firstName","value":"Jillian"},{"name":"middleName","value":""},{"name":"lastName","value":"Harper"}]
        Customer customer = new Customer();

        JsonArray jsonArray;
        try (JsonReader jsonReader = Json.createReader(
                new StringReader(customerJson))) {
            jsonArray = jsonReader.readArray();

        }

        for (JsonValue jsonValue : jsonArray) {
            JsonObject jsonObject = (JsonObject) jsonValue;
            String propertyName = jsonObject.getString("name");
            String propertyValue = jsonObject.getString("value");

            switch (propertyName) {
                case "salutation":
                    customer.setSalutation(propertyValue);
                    break;
                case "firstName":
                    customer.setFirstName(propertyValue);
                    break;
                case "middleName":
                    customer.setMiddleName(propertyValue);
                    break;
                case "lastName":
                    customer.setLastName(propertyValue);
                    break;
                default:
                    LOG.log(Level.WARNING, String.format("Unknown property name found: %s", propertyName));
                    break;

            }
        }

        return customer;
    }

}
