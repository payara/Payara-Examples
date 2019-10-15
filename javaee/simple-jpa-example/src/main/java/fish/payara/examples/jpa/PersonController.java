package fish.payara.examples.jpa;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/api/person")
@ApplicationScoped
public class PersonController {

    private static final Logger LOG = Logger.getLogger(PersonController.class.getName());

    @Inject
    private PersonRepository personRepository;

    @POST
    public Response createPerson(Person person) {
        LOG.log(Level.FINE, "REST request to save Person : {0}", person);
        personRepository.create(person);
        return Response.ok(person).build();
    }

    @PUT
    public Response updatePerson(Person person) {
        LOG.log(Level.FINE, "REST request to update Person : {0}", person);
        personRepository.edit(person);
        return Response.ok(person).build();
    }

    @GET
    public List<Person> getAllPeople() {
        LOG.log(Level.FINE, "REST request to get all People");
        List<Person> people = personRepository.findAll();
        return people;
    }

    @GET
    @Path("/{id}")
    public Response getPerson(@PathParam("id") Long id) {
        LOG.log(Level.FINE, "REST request to get Person : {0}", id);
        Person person = personRepository.find(id);
        if (person == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok(person).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response removePerson(@PathParam("id") Long id) {
        LOG.log(Level.FINE, "REST request to delete Person : {0}", id);
        personRepository.remove(personRepository.find(id));
        return Response.ok().build();
    }

}
