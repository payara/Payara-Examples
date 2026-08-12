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
package fish.payara.examples.resource;

import fish.payara.examples.domain.Patron;
import fish.payara.examples.service.PatronService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("patrons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PatronResource {

    @Inject
    private PatronService patronService;

    @GET
    public Response getAllPatrons() {
        List<Patron> patrons = patronService.findAll();
        return Response.ok(patrons).build();
    }

    @GET
    @Path("{id}")
    public Response getPatronById(@PathParam("id") String id) {
        Patron patron = patronService.find(id);
        if (patron == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(patron).build();
    }

    @POST
    public Response createPatron(Patron patron, @Context UriInfo uriInfo) {
        patronService.create(patron);
        URI uri = uriInfo.getAbsolutePathBuilder().path(patron.getPatronID()).build();
        return Response.created(uri).entity(patron).build();
    }

    @PUT
    @Path("{id}")
    public Response updatePatron(@PathParam("id") String id, Patron patron) {
        Patron existing = patronService.find(id);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        patron.setPatronID(id);
        patronService.edit(patron);
        return Response.ok(patron).build();
    }

    @DELETE
    @Path("{id}")
    public Response deletePatron(@PathParam("id") String id) {
        Patron patron = patronService.find(id);
        if (patron == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        patronService.remove(patron);
        return Response.noContent().build();
    }
}
