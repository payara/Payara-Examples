/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2017-2022 Payara Foundation and/or its affiliates. All rights reserved.
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

import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("sample")
public class SampleController {

    private static final Logger LOGGER = Logger.getLogger(SampleController.class.getName());

    @Inject
    private SecurityContext securityContext;

    @GET
    @Path("read")
    @PermitAll
    public Response read() {
        LOGGER.log(Level.INFO, "read");
        JsonObject result = Json.createObjectBuilder()
                .add("user", securityContext.getCallerPrincipal() != null
                        ? securityContext.getCallerPrincipal().getName() : "Anonymous")
                .add("message", "Read resource")
                .build();
        return Response.ok(result).build();
    }

    @POST
    @Path("write")
//    @RolesAllowed({USER, ADMIN})
    public Response write() {
        LOGGER.log(Level.INFO, "write");
        JsonObject result = Json.createObjectBuilder()
                .add("user", securityContext.getCallerPrincipal().getName())
                .add("message", "Write resource")
                .build();
        return Response.ok(result).build();
    }

    @DELETE
    @Path("delete")
//    @RolesAllowed({ADMIN})
    public Response delete() {
        LOGGER.log(Level.INFO, "delete");
        JsonObject result = Json.createObjectBuilder()
                .add("user", securityContext.getCallerPrincipal().getName())
                .add("message", "Delete resource")
                .build();
        return Response.ok(result).build();
    }
}
