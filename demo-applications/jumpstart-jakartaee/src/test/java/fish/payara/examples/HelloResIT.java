/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2022 Payara Foundation and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://github.com/payara/Payara/blob/master/LICENSE.txt
 * See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * The Payara Foundation designates this particular file as subject to the "Classpath"
 * exception as provided by the Payara Foundation in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package fish.payara.examples;

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
			.withExposedService(serviceName, targetPort,
					Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)))
			.withExposedService(serviceName, 4848,
					Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)));

	@BeforeAll
	public static void initAll() {
		httpClient = HttpClient.newBuilder().build();
		var serviceName = "app_1";
		var targetPort = 8080;
		var resourcePathTemplate = "http://%s:%d/jee-jumpstart/api";

		composeContainer.start();

		restUrl = String.format(resourcePathTemplate,
				composeContainer.getServiceHost(serviceName, targetPort),
				composeContainer.getServicePort(serviceName, targetPort));

		jsonb = JsonbBuilder.create();


	}

	@Test
	void testHello() throws Exception {
		var request = HttpRequest.newBuilder()
				.uri(URI.create(restUrl + "/hello-world/John"))
				.header("Content-Type", "application/json")
				.GET().build();
		log.log(Level.INFO, request.uri().toURL()::toExternalForm);

		var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		assertNotNull(response);
		assertNotNull(response.body());

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
