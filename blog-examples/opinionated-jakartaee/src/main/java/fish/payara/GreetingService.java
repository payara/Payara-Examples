package fish.payara;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.extern.java.Log;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@ApplicationScoped
@Log
public class GreetingService {

	@Inject
	@ConfigProperty(name = "jakarta.version")
	@Getter
	private String jakartaVersion;

	@Inject
	@ConfigProperty(name = "application.server")
	@Getter
	private String applicationServer;



	public JsonObject greet(final String name) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return Json.createObjectBuilder()
				.add("greeting", "Hello there " + name)
				.add("message", "Getting started with Jakarta EE!")
				.add("platform", "Jakarta EE")
				.add("platformVersion", jakartaVersion)
				.add("implementation", applicationServer)
				.add("date", LocalDateTime.now(ZoneOffset.UTC).format(formatter))
				.build();

	}
}
