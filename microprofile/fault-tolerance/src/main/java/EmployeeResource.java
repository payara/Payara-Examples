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

    private final long TIMEOUT = 500;
    private final long SLEEPTIME = 1000;
    private long retryCounter = 0;

    private List<String> employees = Arrays.asList(
            "John",
            "Paul",
            "George",
            "Ringo");

    @GET
    @Path("{id}")
    @Retry(maxRetries = 4, retryOn = {RuntimeException.class})
    public String getEmployeeById(@PathParam("id") int id) {
        System.out.println("Called getEmployeeById a total of " + ++retryCounter + " times");
        if (id >= employees.size()) return "No such employee. Try a number lower than " + employees.size();
        if (isDown()) throw new RuntimeException();
        return employees.get(id);
    }

    private boolean isDown() {
        // approx 80% chance
        return Math.random() > 0.2;
    }

    @GET
    @Fallback(fallbackMethod = "getAllEmployeesFallback")
    @Timeout(TIMEOUT)
    public String getAllEmployees() throws InterruptedException {
        if (isSlow()) return employees.toString();
        return employees.toString();
    }

    public String getAllEmployeesFallback() {
        return "It took longer than expected to get all employees. Try again later!";
    }

    private boolean isSlow() throws InterruptedException {
        if (Math.random() > 0.4) {
            // approx 60% chance
            Thread.sleep(SLEEPTIME);
            return true;
        }
        return false;
    }
}
