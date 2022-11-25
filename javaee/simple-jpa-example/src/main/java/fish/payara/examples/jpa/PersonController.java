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
package fish.payara.examples.jpa;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/api/person")
@ApplicationScoped
public class PersonController {

    private static final Logger LOG = Logger.getLogger(PersonController.class.getName());

    @Inject
    private PersonRepository personRepository;

    @POST
    public Response createPerson(Person person) {
        LOG.log(Level.FINE, "REST request to save Person : {0}", person);
        personRepository.create(person);
        return Response.ok(person).build();
    }

    @PUT
    public Response updatePerson(Person person) {
        LOG.log(Level.FINE, "REST request to update Person : {0}", person);
        personRepository.edit(person);
        return Response.ok(person).build();
    }

    @GET
    public List<Person> getAllPeople() {
        LOG.log(Level.FINE, "REST request to get all People");
        List<Person> people = personRepository.findAll();
        return people;
    }

    @GET
    @Path("/{id}")
    public Response getPerson(@PathParam("id") Long id) {
        LOG.log(Level.FINE, "REST request to get Person : {0}", id);
        Person person = personRepository.find(id);
        if (person == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok(person).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response removePerson(@PathParam("id") Long id) {
        LOG.log(Level.FINE, "REST request to delete Person : {0}", id);
        personRepository.remove(personRepository.find(id));
        return Response.ok().build();
    }

}
