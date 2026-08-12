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
package fish.payara.examples.rest;

import fish.payara.examples.domain.Librarian;
import fish.payara.examples.service.LibrarianService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("librarians")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class LibrarianResource {

    @Inject
    private LibrarianService librarianService;

    @Context
    private UriInfo uriInfo;

    @GET
    public Response getAllLibrarians() {
        List<Librarian> librarians = librarianService.findAll();
        return Response.ok(librarians).build();
    }

    @GET
    @Path("{librarianID}")
    public Response getLibrarian(@PathParam("librarianID") String librarianID) {
        Librarian librarian = librarianService.find(librarianID);
        if (librarian == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(librarian).build();
    }

    @POST
    public Response createLibrarian(Librarian librarian) {
        librarianService.create(librarian);
        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(librarian.getLibrarianID())
                .build();
        return Response.created(uri).entity(librarian).build();
    }

    @PUT
    @Path("{librarianID}")
    public Response updateLibrarian(@PathParam("librarianID") String librarianID, Librarian updatedLibrarian) {
        Librarian existing = librarianService.find(librarianID);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        updatedLibrarian.setLibrarianID(librarianID);
        Librarian result = librarianService.edit(updatedLibrarian);
        return Response.ok(result).build();
    }

    @DELETE
    @Path("{librarianID}")
    public Response deleteLibrarian(@PathParam("librarianID") String librarianID) {
        Librarian librarian = librarianService.find(librarianID);
        if (librarian == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        librarianService.remove(librarian);
        return Response.noContent().build();
    }
}
