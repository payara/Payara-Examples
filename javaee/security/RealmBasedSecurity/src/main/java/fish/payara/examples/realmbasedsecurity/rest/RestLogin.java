/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) [2022] Payara Foundation and/or its affiliates. All rights reserved.
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
package fish.payara.examples.realmbasedsecurity.rest;

import fish.payara.examples.realmbasedsecurity.rest.model.LoginInfo;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

/**
 * Rest API for login, logout.
 *
 * @author aubi
 */
@Path("v1/security")
public class RestLogin {

    @Inject
    private HttpServletRequest request;

    // GET for login is a SECURITY ISSUE! Here it is used only for this demo to use only browser.
    @GET
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(@QueryParam("username") String username, @QueryParam("password") String password) {
        // create session
        request.getSession(true);
        try {
            // login
            request.login(username, password);
            return Response.ok("Login OK").build();
        } catch (ServletException ex) {
            Logger.getLogger(RestLogin.class.getName()).log(Level.SEVERE, ex, () -> "Login failed for user '" + username + "' with reason: " + ex.getMessage());
            return Response.status(401 /*Unauthorized*/, "Login failed with reason: " + ex.getMessage()).build();
        }
    }

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(LoginInfo loginInfo) {
        String username = loginInfo.getUsername();
        String password = loginInfo.getPassword();
        try {
            // create session
            request.getSession(true);
            // login
            request.login(username, password);
            return Response.ok("Login OK").build();
        } catch (ServletException ex) {
            Logger.getLogger(RestLogin.class.getName()).log(Level.SEVERE, ex, () -> "Login failed for user '" + username + "' with reason: " + ex.getMessage());
            return Response.status(401 /*Unauthorized*/, "Login failed with reason: " + ex.getMessage()).build();
        }
    }

    @GET
    @Path("logout")
    @Produces(MediaType.TEXT_PLAIN)
    public Response logout() {
        try {
            request.logout();
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            // A good idea is to remove also the cookie from client
            NewCookie removeJSessionIDCookie = new NewCookie("JSESSIONID", null, request.getContextPath(), "", NewCookie.DEFAULT_VERSION, null, 0, new Date(), false, true);
            return Response
                    .ok("User logged out.")
                    .cookie(removeJSessionIDCookie)
                    .build();
        } catch (ServletException ex) {
            Logger.getLogger(RestLogin.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return Response.status(500, "Logout failed with reason: " + ex.getMessage()).build();
        }
    }
}
