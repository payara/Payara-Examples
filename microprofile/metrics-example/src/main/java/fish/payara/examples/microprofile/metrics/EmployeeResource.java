package fish.payara.examples.microprofile.metrics;

import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Metric;
import org.eclipse.microprofile.metrics.ConcurrentGauge;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.ArrayList;
import javax.ws.rs.core.MediaType;

/**
 * @author Mike Croft
 */

@Path("employees")
@ApplicationScoped
public class EmployeeResource {

    private ArrayList<String> employees = new ArrayList<String>() {{
        add("John");
        add("Paul");
        add("George");
        add("Ringo");
    }};

    @Inject
    @Metric
    ConcurrentGauge employeeCount;

    @PostConstruct
    private void init() {
        // initialise counter with beginning number
       for (int i = 1; i <= 4; i++) {
           employeeCount.inc();
       }        
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

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public int getNumberOfEmployees() {
        return employees.size();
    }

    @POST
    @Metered
    public void createEmployee(@FormParam("name") String emp) {
        if (employees.add(emp)) employeeCount.inc();
    }

    @DELETE
    @Path("{id}")
    @Metered
    public void deleteEmployee(@PathParam("id") int id) {
        if (id >= employees.size()) {
            return;
        } else {
            employees.remove(id);
            employeeCount.dec();
        }
    }

}
