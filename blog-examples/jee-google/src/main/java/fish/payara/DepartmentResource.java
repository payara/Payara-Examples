package fish.payara;

import fish.payara.controllers.PersistenceController;
import fish.payara.entities.Department;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("departments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DepartmentResource {

	@Inject
	PersistenceController controller;
	@POST
	public Department saveDepartment(@Valid final Department department) {
		return controller.saveDepartment(department);

	}

	@GET
	@Path("{businessKey}")
	public Department findDepartment(@NotEmpty @PathParam("businessKey") final String businessKey) {
		return controller.findDepartment(businessKey);
	}

}
