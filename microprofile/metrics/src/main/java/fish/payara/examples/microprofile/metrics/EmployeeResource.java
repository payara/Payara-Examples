package fish.payara.examples.microprofile.metrics;

import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Metric;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mike Croft
 */

@Path("employees")
@ApplicationScoped
public class EmployeeResource {

    private List<String> employees = Arrays.asList(
            "John",
            "Paul",
            "George",
            "Ringo");

    @Inject
    @Metric
    Counter employeeCount;

    @PostConstruct
    private void init(){
        // initialise counter with beginning number
        employeeCount.inc(4);
    }

    @GET
    @Path("{id}")
    public String getEmployeeById(@PathParam("id") int id) {
        return employees.get(id);
    }

    @GET
    public String getAllEmployees() throws InterruptedException {
        return employees.toString();
    }

    @POST
    @Metered
    public void createEmployee(@FormParam("name") String emp) {
        if (employees.add(emp)) employeeCount.inc();
    }

    @DELETE
    @Path("{id}")
    @Metered
    public void deleteEmployee(@PathParam("id") int id){
        if (id >= employees.size()) {
            return;
        } else {
            employees.remove(id);
            employeeCount.dec();
        }
    }

}
