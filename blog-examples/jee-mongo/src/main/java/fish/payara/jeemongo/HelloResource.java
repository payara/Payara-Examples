package fish.payara.jeemongo;

import fish.payara.controllers.DataController;
import fish.payara.entities.HelloEntity;
import fish.payara.entities.TestRecord;
import jakarta.inject.Inject;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HelloResource {

	@Inject
	private DataController controller;
	@GET
	@Produces("text/plain")
	public String hello() {

		return "Hello, World!";
	}

	@GET
	@Path("{name}")
	public HelloEntity greet(@PathParam("name") final String name) {
		TestRecord testRecord = new TestRecord("Luqman", "Hello");
		System.out.println(JsonbBuilder.create().toJson(testRecord));
		return controller.greet(name);
	}
}