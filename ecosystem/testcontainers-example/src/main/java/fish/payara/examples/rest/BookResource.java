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

import fish.payara.examples.domain.Book;
import fish.payara.examples.service.BookService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("books")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class BookResource {

    @Inject
    private BookService bookService;

    @Context
    private UriInfo uriInfo;

    @GET
    public Response getAllBooks() {
        List<Book> books = bookService.findAll();
        return Response.ok(books).build();
    }

    @GET
    @Path("{isbn}")
    public Response getBook(@PathParam("isbn") String isbn) {
        Book book = bookService.find(isbn);
        if (book == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(book).build();
    }

    @POST
    public Response createBook(Book book) {
        bookService.create(book);
        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(book.getIsbn())
                .build();
        return Response.created(uri).entity(book).build();
    }

    @PUT
    @Path("{isbn}")
    public Response updateBook(@PathParam("isbn") String isbn, Book updatedBook) {
        Book existing = bookService.find(isbn);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        updatedBook.setIsbn(isbn);
        Book result = bookService.edit(updatedBook);
        return Response.ok(result).build();
    }

    @DELETE
    @Path("{isbn}")
    public Response deleteBook(@PathParam("isbn") String isbn) {
        Book book = bookService.find(isbn);
        if (book == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        bookService.remove(book);
        return Response.noContent().build();
    }
}
