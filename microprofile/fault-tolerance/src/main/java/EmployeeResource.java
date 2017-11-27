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

    private List<String> employees = Arrays.asList(
            "John",
            "Paul",
            "George",
            "Ringo");

    @GET
    @Path("{id}")
    public String getEmployeeById(@PathParam("id") int id){
        return employees.get(id);
    }

    @GET
    public String getAllEmployees(){
        return employees.toString();
    }

}
