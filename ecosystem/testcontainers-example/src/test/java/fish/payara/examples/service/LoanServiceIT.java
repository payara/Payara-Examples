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

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoanServiceIT extends AbstractServiceIT {

    @Override
    protected String resourcePath() {
        return "resources/loans";
    }

    @Test
    @Order(1)
    void testCreateAndRetrieveLoan() {
        String librarianId = createLibrarian("Loan IT Librarian");
        String patronId = createPatron("Loan IT Patron");
        String isbn = createBook("Loan IT Book");

        Response createResponse = createLoan(loanBody(librarianId, patronId, isbn,
                "2026-07-14T09:00:00", "2026-07-28T09:00:00"));

        assertEquals(Response.Status.CREATED.getStatusCode(), createResponse.getStatus());
        String location = createResponse.getHeaderString("Location");
        assertNotNull(location, "Location header missing after create");

        Map<String, Object> retrieved = client.target(location)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<Map<String, Object>>() {});

        assertEquals("2026-07-14T09:00:00", retrieved.get("loanDate"));
        assertEquals("2026-07-28T09:00:00", retrieved.get("returnDate"));
        assertEquals(librarianId, asMap(retrieved.get("librarian")).get("librarianID"));
        assertEquals(patronId, asMap(retrieved.get("patron")).get("patronID"));
        assertEquals(isbn, asMap(retrieved.get("book")).get("isbn"));
    }

    @Test
    @Order(2)
    void testFindAllLoans() {
        Response response = baseTarget.request(MediaType.APPLICATION_JSON).get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        List<Map<String, Object>> loans = response.readEntity(new GenericType<List<Map<String, Object>>>() {});
        assertNotNull(loans);
        assertFalse(loans.isEmpty(), "Expected at least the loan created by testCreateAndRetrieveLoan");
    }

    @Test
    @Order(3)
    void testUpdateLoan() {
        String librarianId = createLibrarian("Loan IT Librarian Update");
        String patronId = createPatron("Loan IT Patron Update");
        String isbn = createBook("Loan IT Book Update");

        Response createResponse = createLoan(loanBody(librarianId, patronId, isbn,
                "2026-07-14T09:00:00", "2026-07-28T09:00:00"));
        String location = createResponse.getHeaderString("Location");
        assertNotNull(location, "Location header missing after create");

        Map<String, Object> updateBody = loanBody(librarianId, patronId, isbn,
                "2026-07-14T09:00:00", "2026-08-04T09:00:00");

        Response updateResponse = client.target(location)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(updateBody, MediaType.APPLICATION_JSON));

        assertEquals(Response.Status.OK.getStatusCode(), updateResponse.getStatus());

        Map<String, Object> retrieved = client.target(location)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<Map<String, Object>>() {});

        assertEquals("2026-08-04T09:00:00", retrieved.get("returnDate"));
    }

    @Test
    @Order(4)
    void testDeleteLoan() {
        String librarianId = createLibrarian("Loan IT Librarian Delete");
        String patronId = createPatron("Loan IT Patron Delete");
        String isbn = createBook("Loan IT Book Delete");

        Response createResponse = createLoan(loanBody(librarianId, patronId, isbn,
                "2026-07-14T09:00:00", "2026-07-28T09:00:00"));
        String location = createResponse.getHeaderString("Location");
        assertNotNull(location, "Location header missing after create");

        Response deleteResponse = client.target(location).request().delete();
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), deleteResponse.getStatus());

        Response getResponse = client.target(location)
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), getResponse.getStatus());
    }

    private Response createLoan(Map<String, Object> loan) {
        return baseTarget.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(loan, MediaType.APPLICATION_JSON));
    }

    private Map<String, Object> loanBody(String librarianId, String patronId, String isbn,
                                          String loanDate, String returnDate) {
        Map<String, Object> loan = new LinkedHashMap<>();
        loan.put("loanDate", loanDate);
        loan.put("returnDate", returnDate);
        loan.put("librarian", singleField("librarianID", librarianId));
        loan.put("patron", singleField("patronID", patronId));
        loan.put("book", singleField("isbn", isbn));
        return loan;
    }

    private Map<String, Object> singleField(String key, String value) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(key, value);
        return map;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> asMap(Object value) {
        return (Map<String, Object>) value;
    }

    private String createLibrarian(String name) {
        Map<String, Object> librarian = new LinkedHashMap<>();
        librarian.put("name", name);

        Response response = client.target(applicationContextUrl() + "resources/librarians")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(librarian, MediaType.APPLICATION_JSON));
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus(),
                "Failed to create prerequisite librarian");

        Map<String, Object> created = response.readEntity(new GenericType<Map<String, Object>>() {});
        return (String) created.get("librarianID");
    }

    private String createPatron(String name) {
        Map<String, Object> patron = new LinkedHashMap<>();
        patron.put("name", name);

        Response response = client.target(applicationContextUrl() + "resources/patrons")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(patron, MediaType.APPLICATION_JSON));
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus(),
                "Failed to create prerequisite patron");

        Map<String, Object> created = response.readEntity(new GenericType<Map<String, Object>>() {});
        return (String) created.get("patronID");
    }

    private String createBook(String title) {
        Map<String, Object> book = new LinkedHashMap<>();
        book.put("title", title);
        book.put("author", "Loan IT Author");
        book.put("pages", 100);

        Response response = client.target(applicationContextUrl() + "resources/books")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(book, MediaType.APPLICATION_JSON));
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus(),
                "Failed to create prerequisite book");

        Map<String, Object> created = response.readEntity(new GenericType<Map<String, Object>>() {});
        return (String) created.get("isbn");
    }
}
