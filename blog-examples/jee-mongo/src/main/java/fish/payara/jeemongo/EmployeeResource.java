package fish.payara.jeemongo;

import fish.payara.controllers.DataController;
import fish.payara.entities.Employee;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/employee")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {
    @Inject
    private DataController dataController;

    @POST
    @Path("{departmentCode}")
    public Employee saveEmployee(@NotEmpty @PathParam("departmentCode") final String departmentCode,
                                 @NotNull @Valid final Employee employee) {
        return dataController.saveEmployee(departmentCode, employee);
    }

    @GET
    @Path("{departmentCode}")
    public List<Employee> loadEmployeesByDepartment(@NotBlank @PathParam("departmentCode") final String departmentCode) {
        return dataController.loadEmployeesByDepartment(departmentCode);
    }

    @GET
    @Path("search")
    public List<Employee> search(@BeanParam final SearchBeanParam beanParam) {
        return dataController.search(beanParam);
    }

    @GET
    @Path("search/text/{searchTerm}")
    public List<Employee> textSearch(@PathParam("searchTerm") @NotBlank final String searchTerm) {
        return dataController.textSearch(searchTerm);
    }

}
