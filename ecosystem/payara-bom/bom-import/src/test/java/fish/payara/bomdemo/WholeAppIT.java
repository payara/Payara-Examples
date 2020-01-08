/*
 *    DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 *    Copyright (c) [2019] Payara Foundation and/or its affiliates. All rights reserved.
 *
 *    The contents of this file are subject to the terms of either the GNU
 *    General Public License Version 2 only ("GPL") or the Common Development
 *    and Distribution License("CDDL") (collectively, the "License").  You
 *    may not use this file except in compliance with the License.  You can
 *    obtain a copy of the License at
 *    https://github.com/payara/Payara/blob/master/LICENSE.txt
 *    See the License for the specific
 *    language governing permissions and limitations under the License.
 *
 *    When distributing the software, include this License Header Notice in each
 *    file and include the License file at glassfish/legal/LICENSE.txt.
 *
 *    GPL Classpath Exception:
 *    The Payara Foundation designates this particular file as subject to the "Classpath"
 *    exception as provided by the Payara Foundation in the GPL Version 2 section of the License
 *    file that accompanied this code.
 *
 *    Modifications:
 *    If applicable, add the following below the License Header, with the fields
 *    enclosed by brackets [] replaced by your own identifying information:
 *    "Portions Copyright [year] [name of copyright owner]"
 *
 *    Contributor(s):
 *    If you wish your version of this file to be governed by only the CDDL or
 *    only the GPL Version 2, indicate your decision by adding "[Contributor]
 *    elects to include this software in this distribution under the [CDDL or GPL
 *    Version 2] license."  If you don't indicate a single choice of license, a
 *    recipient has the option to distribute your version of this file under
 *    either the CDDL, the GPL Version 2 or to extend the choice of license to
 *    its licensees as provided above.  However, if you add GPL Version 2 code
 *    and therefore, elected the GPL Version 2 license, then the option applies
 *    only if the new code is made subject to such option by the copyright
 *    holder.
 */

package fish.payara.bomdemo;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class WholeAppIT {
    @Deployment
    public static WebArchive deployWholeApp() throws IOException {
        Path packageResult = Files.list(Paths.get("target"))
                .filter(p -> p.getFileName().toString().endsWith(".war"))
                .findAny().orElseThrow(() -> new IllegalStateException("No .war file in target directory. Run 'mvn verify'"));
        return ShrinkWrap.createFromZipFile(WebArchive.class, packageResult.toFile());
    }

    @ArquillianResource
    URI baseUrl;

    @Test
    public void helloControllerGreets() {
        String greeting = ClientBuilder.newClient().target(baseUrl).path("data/hello").request().get(String.class);
        assertEquals("Hello World", greeting);
    }

    @Test
    public void healthCheckUp() {
        WebTarget healthPath = ClientBuilder.newClient().target(baseUrl.resolve("/")).path("health");
        JsonObject health = healthPath
                .request(MediaType.APPLICATION_JSON_TYPE).get(JsonObject.class);
        assertNotNull(health);
        //   {"status":"UP","checks":[{"name":"ServiceHealthCheck","status":"UP","data":{}}]}]]
        assertEquals("UP", health.getString("status"));
        JsonObject serviceHealthCheck = health.getJsonArray("checks").stream()
                .filter(v -> v.getValueType() == JsonValue.ValueType.OBJECT)
                .map(JsonValue::asJsonObject)
                .filter(v -> v.getString("name").equals("ServiceHealthCheck"))
                .findAny()
                .orElseThrow(() -> new AssertionError("ServiceHealthCheck status not found in " + health));
        assertEquals("UP", serviceHealthCheck.getString("status"));
    }

    @Test
    public void metricMeasures() {
        Client client = ClientBuilder.newClient();
        WebTarget metricEndpoint = client.target(baseUrl).path("data/metric");

        long counter = metricEndpoint.path("increment").request().get(Long.class);
        assertTrue("counter should be at least 1, is "+counter, counter > 0);

        WebTarget metricsPath = client.target(baseUrl.resolve("/")).path("metrics");
        JsonObject metrics = metricsPath
                .request(MediaType.APPLICATION_JSON_TYPE).get(JsonObject.class);
        assertNotNull(metrics);
        /*
         {
          "vendor": {
            "system.cpu.load": 0.6216716209333485
          },
          "base": {
            // ...
          },
          "application": {
            "fish.payara.bomdemo.metric.MetricController.endpoint_counter": 1,
            "fish.payara.bomdemo.metric.MetricController.timed-request": {
              "fiveMinRate": 0.0,
              "max": 0,
              "count": 0,
              "p50": 0.0,
              "p95": 0.0,
              "p98": 0.0,
              "p75": 0.0,
              "p99": 0.0,
              "min": 0,
              "fifteenMinRate": 0.0,
              "meanRate": 0.0,
              "mean": 0.0,
              "p999": 0.0,
              "oneMinRate": 0.0,
              "stddev": 0.0
            }
          }
        }
        */
        assertEquals(counter, metrics.getJsonObject("application").getJsonNumber("fish.payara.bomdemo.metric.MetricController.endpoint_counter").longValue());
        assertNotNull(metrics.getJsonObject("application").getJsonObject("fish.payara.bomdemo.metric.MetricController.timed-request"));
    }

    @Test
    @RunAsClient // we'll run this test from client, with matching Jersey client we obtained from BOM
    public void configConfigures() {
        Client client = ClientBuilder.newClient();
        WebTarget configEndpoint = client.target(baseUrl).path("data/config/");

        String configResult = configEndpoint.path("injected").request().get(String.class);
        assertEquals("Config value as Injected by CDI Injected value", configResult);
    }
}
