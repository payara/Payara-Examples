package fish.payara;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.logging.Level;

import lombok.extern.java.Log;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import jakarta.json.JsonObject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

@Log
@Testcontainers
@Disabled
public class HelloResIT {

	protected static HttpClient httpClient;
	protected static String restUrl;
	final static String serviceName = "app_1";
	final static int targetPort = 8080;
	static Jsonb jsonb;
	@Container
	public static DockerComposeContainer composeContainer = new DockerComposeContainer(
			new File("docker-compose.yml"))
			.withTailChildContainers(true)
			.withExposedService(serviceName, targetPort)
			.withStartupTimeout(Duration.ofMinutes(1));


	@BeforeAll
	public static void initAll() {
		httpClient = HttpClient.newBuilder().build();
		var resourcePathTemplate = "http://%s:%d/t11-jumpstart/api";

//		composeContainer.start();

		restUrl = String.format(resourcePathTemplate,
				composeContainer.getServiceHost(serviceName, targetPort),
				composeContainer.getServicePort(serviceName, targetPort),
				serviceName);

		jsonb = JsonbBuilder.create();


	}

	@Test
	void testHello() throws Exception {
		var request = HttpRequest.newBuilder()
				.uri(URI.create(restUrl + "/hello-world/John"))
				.header("Content-Type", "application/json")
				.GET().build();
		log.log(Level.INFO, request.uri().toURL()::toExternalForm);
		System.out.println("---");
		var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		assertNotNull(response);
		assertNotNull(response.body());
		assertEquals(200, response.statusCode());

		log.log(Level.INFO, response::body);

		JsonObject jsonObject = jsonb.fromJson(response.body(), JsonObject.class);

		assertNotNull(jsonObject);
		assertEquals("Hello there John", jsonObject.getString("greeting"));
		assertEquals("Getting started with Jakarta EE!", jsonObject.getString("message"));
		assertEquals("Jakarta EE", jsonObject.getString("platform"));
		assertEquals("10.0.0", jsonObject.getString("platformVersion"));
		assertEquals("payara-6-community", jsonObject.getString("implementation"));
		assertNotNull(jsonObject.get("date"));

		log.log(Level.INFO, jsonObject::toString);
	}
}
