package fish.payara.jeemongo;

import fish.payara.controllers.DataController;
import fish.payara.entities.Department;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/department")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DepartmentResource {

	@Inject
	DataController dataController;
	@POST
	public Department save(@NotNull @Valid Department department) {
		return dataController.saveDepartment(department);
	}
}
