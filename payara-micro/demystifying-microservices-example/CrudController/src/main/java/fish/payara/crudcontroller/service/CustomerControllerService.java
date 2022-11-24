/*
 * DO NOT ALTER OR REMOTE COPYRIGHT NOTICES OR THIS HEADER.
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
package fish.payara.crudcontroller.service;

import fish.payara.crudcontroller.dto.Customer;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.OPTIONS;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import fish.payara.crudcontroller.restclient.CustomerPersistenceClient;
import jakarta.inject.Inject;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/customercontroller")
@RequestScoped
public class CustomerControllerService {

    @Inject
    @RestClient
    private CustomerPersistenceClient customerPersistenceClient;

    private static final Logger LOG = Logger.getLogger(CustomerControllerService.class.getName());

    @OPTIONS
    public Response options() {
        LOG.log(Level.INFO, "CustomerControllerService.options() invoked");
        return Response.ok("")
                .header("Access-Control-Allow-Origin", "http://localhost:8080")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Add a customer to the database",
            description = "Add a customer to the database based"
            + " on the JSON representation of the customer")
    public Response addCustomer(Customer customer) throws URISyntaxException {

        Response response = null;
        Response persistenceServiceResponse;

        try {

            persistenceServiceResponse = customerPersistenceClient.create(customer);

            if (persistenceServiceResponse.getStatus() == 201) {
                response = Response.ok("{}").
                        header("Access-Control-Allow-Origin", "http://localhost:8080").build();

            } else {
                response = Response.serverError().
                        header("Access-Control-Allow-Origin", "http://localhost:8080").build();
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Exception while processing request", e);
            response = Response.serverError().
                    header("Access-Control-Allow-Origin", "http://localhost:8080").build();
        }

        return response;
    }

}
