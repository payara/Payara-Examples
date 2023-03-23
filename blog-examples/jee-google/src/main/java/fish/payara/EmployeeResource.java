package fish.payara;

import java.util.List;

import fish.payara.controllers.PersistenceController;
import fish.payara.entities.Employee;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("employees")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeResource {

	@Inject
	PersistenceController controller;

	@GET
	@Path("department/{deptBusinessKey}")
	public List<Employee> findAllEmployeesByDepartment(
			@NotEmpty @PathParam("deptBusinessKey") final String businessKey) {
		return controller.findEmployeesByDepartment(businessKey);
	}

	@Path("employee/{departmentBusinessKey}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Employee saveEmployee(@NotEmpty @PathParam("departmentBusinessKey") final String departmentKey,
			@Valid final Employee employee) {
		return controller.saveEmployee(departmentKey, employee);

	}

	@GET
	@Path("{businessKey}")
	public Employee findEmployee(@NotEmpty @PathParam("businessKey") final String businessKey) {
		return controller.findEmployee(businessKey);
	}

}
