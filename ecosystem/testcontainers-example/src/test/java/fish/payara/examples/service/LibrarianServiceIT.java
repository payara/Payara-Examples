/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2026 Payara Foundation and/or its affiliates. All rights reserved.
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
package fish.payara.examples.service;

import fish.payara.examples.domain.Librarian;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LibrarianServiceIT extends AbstractServiceIT {

    @Override
    protected String resourcePath() {
        return "resources/librarians";
    }

    @Test
    @Order(1)
    void testCreateAndRetrieveLibrarian() throws Exception {
        Librarian librarian = new Librarian();
        librarian.setName("Alice Johnson");

        Response createResponse = null;
        String respBody = null;

        for (int i = 0; i < 20; i++) {
            createResponse = baseTarget
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(librarian, MediaType.APPLICATION_JSON));
            if (createResponse.getStatus() != 404) {
                break;
            }
            Thread.sleep(1000);
        }

        if (createResponse.getStatus() != Response.Status.CREATED.getStatusCode()) {
            try {
                respBody = createResponse.readEntity(String.class);
            } catch (Exception e) {
                respBody = "<no body>";
            }
            String logs = containerLogs();
            String msg = String.format("POST failed, status=%d, body=%s, container logs:\n%s", createResponse.getStatus(), respBody, logs);
            assertEquals(Response.Status.CREATED.getStatusCode(), createResponse.getStatus(), msg);
        }

        String location = createResponse.getHeaderString("Location");
        assertNotNull(location, "Location header missing after create");

        Response getResponse = client.target(location)
                .request(MediaType.APPLICATION_JSON)
                .get();

        assertEquals(Response.Status.OK.getStatusCode(), getResponse.getStatus());
        Librarian retrieved = getResponse.readEntity(Librarian.class);
        assertNotNull(retrieved);
        assertEquals("Alice Johnson", retrieved.getName());
    }

    @Test
    @Order(2)
    void testFindAllLibrarians() throws Exception {
        Response response = null;
        String respBody = null;

        for (int i = 0; i < 20; i++) {
            response = baseTarget.request(MediaType.APPLICATION_JSON).get();
            if (response.getStatus() != 404) {
                break;
            }
            Thread.sleep(500);
        }

        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            try {
                respBody = response.readEntity(String.class);
            } catch (Exception e) {
                respBody = "<no body>";
            }
            String logs = containerLogs();
            String msg = String.format("GET all failed, status=%d, body=%s, logs:\n%s", response.getStatus(), respBody, logs);
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(), msg);
        }

        List<Librarian> librarians = response.readEntity(new GenericType<List<Librarian>>() {
        });
        assertNotNull(librarians);
        assertFalse(librarians.isEmpty(), "Expected at least one librarian");
    }

    @Test
    @Order(3)
    void testUpdateLibrarian() throws Exception {
        Librarian librarian = new Librarian();
        librarian.setName("Bob Williams");

        Response createResponse = baseTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(librarian, MediaType.APPLICATION_JSON));

        String location = createResponse.getHeaderString("Location");
        assertNotNull(location);

        Librarian created = client.target(location)
                .request(MediaType.APPLICATION_JSON)
                .get(Librarian.class);

        created.setName("Bob Updated");

        Response updateResponse = client.target(location)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(created, MediaType.APPLICATION_JSON));

        assertEquals(Response.Status.OK.getStatusCode(), updateResponse.getStatus());

        Librarian updated = client.target(location)
                .request(MediaType.APPLICATION_JSON)
                .get(Librarian.class);

        assertEquals("Bob Updated", updated.getName());
    }

    @Test
    @Order(4)
    void testDeleteLibrarian() throws Exception {
        Librarian librarian = new Librarian();
        librarian.setName("Charlie Brown");

        Response createResponse = baseTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(librarian, MediaType.APPLICATION_JSON));

        String location = createResponse.getHeaderString("Location");
        assertNotNull(location);

        Response deleteResponse = client.target(location)
                .request()
                .delete();

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), deleteResponse.getStatus());

        Response getResponse = client.target(location)
                .request(MediaType.APPLICATION_JSON)
                .get();

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), getResponse.getStatus());
    }
}
