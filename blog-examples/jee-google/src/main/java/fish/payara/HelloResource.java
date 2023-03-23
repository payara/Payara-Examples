package fish.payara;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import lombok.Getter;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

@Path("/hello-world")
public class HelloResource {

	@Inject
	@ConfigProperty(name = "jakarta.version")
	@Getter
	private String jakartaVersion;

	@Inject
	@ConfigProperty(name = "application.server")
	@Getter
	private String applicationServer;

	@Path("{name}")
	@GET
	public JsonObject greet(@PathParam("name") final String name) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return Json.createObjectBuilder()
				.add("greeting", "Hello there " + name.toUpperCase())
				.add("message", "Getting started with Jakarta EE!")
				.add("platform", "Jakarta EE")
				.add("service", "partner service")
				.add("platformVersion", jakartaVersion)
				.add("implementation", applicationServer)
				.add("date", LocalDateTime.now(ZoneOffset.UTC).format(formatter))
				.build();

	}



}