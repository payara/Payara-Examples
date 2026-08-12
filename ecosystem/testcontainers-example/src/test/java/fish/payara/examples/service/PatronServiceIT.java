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

import fish.payara.examples.domain.Patron;
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
class PatronServiceIT extends AbstractServiceIT {

    @Override
    protected String resourcePath() {
        return "resources/patrons";
    }

    @Test
    @Order(1)
    void testCreateAndRetrievePatron() {
        Patron patron = new Patron();
        patron.setName("John Doe");
        patron.setEmail("john@example.com");

        Response createResponse = baseTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(patron, MediaType.APPLICATION_JSON));

        assertEquals(Response.Status.CREATED.getStatusCode(), createResponse.getStatus());
        String location = createResponse.getHeaderString("Location");
        assertNotNull(location);

        Patron retrieved = client.target(location)
                .request(MediaType.APPLICATION_JSON)
                .get(Patron.class);

        assertNotNull(retrieved);
        assertEquals("John Doe", retrieved.getName());
    }

    @Test
    @Order(2)
    void testFindAllPatrons() {
        Response response = baseTarget.request(MediaType.APPLICATION_JSON).get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        List<Patron> patrons = response.readEntity(new GenericType<>() {
        });
        assertNotNull(patrons);
    }

    @Test
    @Order(3)
    void testUpdatePatron() {
        Patron patron = new Patron();
        patron.setName("Jane Doe");
        patron.setEmail("jane@example.com");

        Response createResponse = baseTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(patron, MediaType.APPLICATION_JSON));

        String location = createResponse.getHeaderString("Location");
        assertNotNull(location, "Location header missing after create");

        patron.setEmail("jane.updated@example.com");
        Response updateResponse = client.target(location)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(patron, MediaType.APPLICATION_JSON));

        assertEquals(Response.Status.OK.getStatusCode(), updateResponse.getStatus());

        Patron updated = client.target(location)
                .request(MediaType.APPLICATION_JSON)
                .get(Patron.class);

        assertEquals("jane.updated@example.com", updated.getEmail());
        assertEquals("Jane Doe", updated.getName());
    }

    @Test
    @Order(4)
    void testDeletePatron() {
        Patron patron = new Patron();
        patron.setName("Mark Twain");
        patron.setEmail("mark@example.com");

        Response createResponse = baseTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(patron, MediaType.APPLICATION_JSON));

        String location = createResponse.getHeaderString("Location");
        assertNotNull(location, "Location header missing after create");

        Response deleteResponse = client.target(location).request().delete();
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), deleteResponse.getStatus());

        Response getResponse = client.target(location)
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), getResponse.getStatus());
    }
}
