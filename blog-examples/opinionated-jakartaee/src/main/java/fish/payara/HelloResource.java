package fish.payara;

import java.io.InputStream;

import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.EntityPart;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)

public class HelloResource {
	@Inject
	private GreetingService greetingService;

	@GET
	@Path("ping")
	public String pint() {
		return "Hello, World!";
	}
	@GET
	@Path("{visitor}")
	public JsonObject hello(@PathParam("visitor") final String visitor) {
		return greetingService.greet(visitor);
	}


	@POST
	@Path("submit/picture")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response postForm(@FormParam("userId") String userId,
			@FormParam("picture") EntityPart pic) {
		String fileName = pic.getFileName().orElseThrow(NotSupportedException::new);
		if (isFileExtension(fileName)) {
			InputStream content = pic.getContent();
			//Do something with the content...
		}
		return Response.ok("Picture uploaded successfully").build();
	}
	private boolean isFileExtension(final String fileName) {
		return fileName.toLowerCase().endsWith(".png")
				|| fileName.toLowerCase().endsWith(".jpg")
				|| fileName.toLowerCase().endsWith(".jpeg");
	}

}