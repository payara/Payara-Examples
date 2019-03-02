package fish.payara.crudpersistence.service;

import fish.payara.crudpersistence.Customer;
import fish.payara.crudpersistence.dao.CrudDao;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@ApplicationScoped
@Path("customerpersistence")
public class CustomerPersistenceService {

    @Context
    private UriInfo uriInfo;

    @Inject
    private CrudDao customerDao;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Customer customer) {
        try {
            customerDao.create(customer);
        } catch (Exception e) {
            return Response.serverError().build();
        }

        return Response.created(uriInfo.getAbsolutePath()).build();
    }

}
