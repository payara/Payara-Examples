import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mike Croft
 */

@Path("employees")
@ApplicationScoped
public class EmployeeResource {

    private final long TIMEOUT = 1000;

    private List<String> employees = Arrays.asList(
            "John",
            "Paul",
            "George",
            "Ringo");

    @GET
    @Path("{id}")
    public String getEmployeeById(@PathParam("id") int id) {
        return employees.get(id);
    }

    @GET
    @Fallback(fallbackMethod = "getAllEmployeesFallback")
    @Retry(maxRetries = 1)
    @Timeout(500)
    public String getAllEmployees() throws InterruptedException {

        if (isSlow()) return employees.toString();

        if (isDown()) throw new RuntimeException();
        return employees.toString();
    }

    public String getAllEmployeesFallback() {
        return "an error occurred in getting all employees!";
    }

    private boolean isDown() {
        return Math.random() > 0.5;
    }

    private boolean isSlow() throws InterruptedException {
        if (Math.random() > 0.5) {
            Thread.sleep(TIMEOUT);
            return true;
        }
        return false;
    }
}
