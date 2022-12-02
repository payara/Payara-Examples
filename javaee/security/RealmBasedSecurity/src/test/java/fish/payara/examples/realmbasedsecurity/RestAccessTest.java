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
package fish.payara.examples.realmbasedsecurity;

import fish.payara.examples.realmbasedsecurity.rest.model.MeInfo;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test access to rest services, accessibility, login, logout, etc.
 *
 * @author aubi
 */
@RunWith(Arquillian.class)
public class RestAccessTest {
    private static final String JSESSIONID_COOKIE_NAME = "JSESSIONID";

    private final static Logger logger = Logger.getLogger(RestAccessTest.class.getName());

    @ArquillianResource
    private URL base;

    private Client client;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addPackages(true, "fish.payara.examples.realmbasedsecurity")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/web.xml"), "web.xml");
        // list all files in the archive
        System.out.println(war.toString(true));
        return war;
    }

    @Before
    public void setup() {
        logger.info("setting client");
        this.client = ClientBuilder.newClient();
    }

    @After
    public void clean() {
        logger.info("close client");
        if (this.client != null) {
            this.client.close();
        }
    }

    @Test
    @RunAsClient
    public void testWrongLogin() throws MalformedURLException {
        String username = "NOTEXISTINGUSER";
        String password = "NOPWD";
        WebTarget target = this.client.target(new URL(this.base, "rest/v1/security/login?username=" + username + "&password=" + password).toExternalForm());
        Response response = target.request().accept(MediaType.TEXT_PLAIN_TYPE).get();
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatusInfo().getStatusCode());
    }

    @Ignore("Needs to setup user 'Adam'.")
    @Test
    @RunAsClient
    public void testRegularUserLoginLogout() throws MalformedURLException {
        MeInfo meInfo;
        meInfo = callAboutMe(null);
        assertEquals("NOT LOGGED IN", meInfo.getUsername());
        String sessionId = login("Adam", "Eve");
        meInfo = callAboutMe(sessionId);
        assertEquals("Adam", meInfo.getUsername());
        assertEquals("Adam is user", true, meInfo.isIsUser());
        assertEquals("Adam is NOT admin", false, meInfo.isIsAdmin());
        logout(sessionId);
    }

    @Ignore("Needs to setup user 'admin'.")
    @Test
    @RunAsClient
    public void testAdminLoginLogout() throws MalformedURLException {
        MeInfo meInfo;
        meInfo = callAboutMe(null);
        assertEquals("NOT LOGGED IN", meInfo.getUsername());
        String sessionId = login("admin", "5FsNqxmBug6x4eR");
        meInfo = callAboutMe(sessionId);
        assertEquals("admin", meInfo.getUsername());
        assertEquals("admin is user", true, meInfo.isIsUser());
        assertEquals("admin is admin", true, meInfo.isIsAdmin());
        logout(sessionId);
    }

    @Test
    @RunAsClient
    public void testUserNotLoggedInRest() throws MalformedURLException {
        MeInfo meInfo = callAboutMe(null);
        assertEquals("NOT LOGGED IN", meInfo.getUsername());
    }

    private MeInfo callAboutMe(String sessionId) throws MalformedURLException {
        WebTarget target = this.client.target(new URL(this.base, "rest/v1/public/me").toExternalForm());
        MeInfo meInfo = target
                .request()
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .cookie(JSESSIONID_COOKIE_NAME, sessionId)
                .get(MeInfo.class);
        logger.log(Level.INFO, "Returned message {0}", new Object[]{meInfo});
        return meInfo;
    }

    private String login(String username, String password) throws MalformedURLException {
        WebTarget target = this.client.target(new URL(this.base, "rest/v1/security/login?username=" + username + "&password=" + password).toExternalForm());
        Response response = target.request().accept(MediaType.TEXT_PLAIN_TYPE).get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatusInfo().getStatusCode());
        String loginMessage = response.readEntity(String.class);
        logger.log(Level.INFO, "Returned message {0}", new Object[]{loginMessage});
        assertEquals("Login OK", loginMessage);
        return response.getCookies().get(JSESSIONID_COOKIE_NAME).getValue();
    }

    private void logout(String sessionId) throws MalformedURLException {
        WebTarget target = this.client.target(new URL(this.base, "rest/v1/security/logout").toExternalForm());
        Response response = target
                .request()
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .cookie(JSESSIONID_COOKIE_NAME, sessionId)
                .get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatusInfo().getStatusCode());
        String logoutMessage = response.readEntity(String.class);
        logger.log(Level.INFO, "Returned message {0}", new Object[]{logoutMessage});
        assertEquals("User logged out.", logoutMessage);
    }

}
