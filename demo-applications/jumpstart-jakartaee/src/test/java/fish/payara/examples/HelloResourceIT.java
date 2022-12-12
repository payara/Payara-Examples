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

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.logging.Level;

import lombok.extern.java.Log;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.asset.UrlAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import fish.payara.examples.jumpstartjee.HelloApplication;
import fish.payara.examples.jumpstartjee.HelloEntity;
import fish.payara.examples.jumpstartjee.HelloResource;
import fish.payara.examples.jumpstartjee.PersistenceService;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

@Log
@ExtendWith(ArquillianExtension.class)
public class HelloResourceIT {

	@Inject
	protected HelloResource helloResource;
	@ArquillianResource
	protected URL contextPath;
	protected static HttpClient httpClient;

	@Inject
	protected PersistenceService persistenceService;

	static Jsonb jsonb;
	@BeforeAll
	public static void initAll() {
		httpClient = HttpClient.newBuilder().build();
		 jsonb = JsonbBuilder.create();

	}

	@Deployment
	public static WebArchive createLocalDeployment() throws URISyntaxException {
		final JavaArchive javaArchive = ShrinkWrap.create(JavaArchive.class, "jumpstart-ee.jar")
				.addPackage(HelloApplication.class.getPackage())
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsManifestResource(new FileAsset(new File("src/test/resources/META-INF/beans.xml")), "beans.xml")
				.addAsResource(
						new UrlAsset(Objects.requireNonNull(
								HelloResourceIT.class.getResource("/microprofile-config.properties"))),
						"/META-INF/microprofile-config.properties");

		javaArchive.getContent().forEach((key, value) -> log.log(Level.INFO, (key + "-->" + value)));
		return ShrinkWrap.create(WebArchive.class, "jakarta-jumpstart.war").addAsLibrary(javaArchive);
	}

	@Test
	void testHello() {
		var jsonObject = helloResource.hello("John Jakes");
		assertNotNull(jsonObject);

		log.log(Level.INFO, jsonObject::toString);


		assertEquals("Hello there John Jakes", jsonObject.getString("greeting"));
		assertEquals("Getting started with Jakarta EE!", jsonObject.getString("message"));
		assertEquals("Jakarta EE", jsonObject.getString("platform"));
		assertEquals("10.0.0", jsonObject.getString("platformVersion"));
		assertEquals("payara-6-community", jsonObject.getString("implementation"));
		assertNotNull(jsonObject.get("date"));

	}

	@Test
	@RunAsClient
	void testHelloResource() throws Exception {
		var request = HttpRequest.newBuilder()
				.uri(URI.create(contextPath.toExternalForm() + "api/hello-world/John"))

				.header("Content-Type", "application/json")
				.GET().build();
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

	@Test
	void testPersistence() {
		final var helloEntity = new HelloEntity();
		helloEntity.setGreeting("Hello, world!");
		helloEntity.setName("Arquillian");
		helloEntity.setGreetingDate(LocalDateTime.now(ZoneOffset.UTC));
		assertNull(helloEntity.getId());
		assertNull(helloEntity.getDateCreated());
		assertNull(helloEntity.getVersion());

		log.log(Level.INFO, () -> jsonb.toJson(helloEntity));

		final var  savedEntity = persistenceService.save(helloEntity);

		assertNotNull(savedEntity);
		assertNotNull(savedEntity.getId());
		assertNotNull(savedEntity.getDateCreated());
		assertNotNull(savedEntity.getVersion());

		assertEquals(helloEntity.getGreeting(), savedEntity.getGreeting());
		assertEquals(helloEntity.getName(), savedEntity.getName());
		assertEquals(helloEntity.getGreetingDate(), savedEntity.getGreetingDate());

		log.log(Level.INFO, () -> jsonb.toJson(savedEntity));

		final var loadedEntity = persistenceService.find(savedEntity.getId());

		assertNotNull(loadedEntity);
		assertNotNull(loadedEntity.getId());
		assertNotNull(loadedEntity.getDateCreated());
		assertNotNull(loadedEntity.getVersion());

		assertEquals(helloEntity.getGreeting(), loadedEntity.getGreeting());
		assertEquals(helloEntity.getName(), loadedEntity.getName());
		assertEquals(helloEntity.getGreetingDate(), loadedEntity.getGreetingDate());

		log.log(Level.INFO, () -> jsonb.toJson(savedEntity));

	}

}
