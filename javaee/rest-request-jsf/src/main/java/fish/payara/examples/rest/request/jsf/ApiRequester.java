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
package fish.payara.examples.rest.request.jsf;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource.Builder;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;

@Named
@Dependent
public class ApiRequester {

    private HttpServletRequest request;

    /**
     * Gets the details of the request that was made to the JSF page to
     * initialise this bean.
     */
    @PostConstruct
    private void getContext() {
        request = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest());
    }

    /**
     * Makes a GET request to a specified endpoint for a local API. E.g. to get
     * a String from the URL http://api.localhost/hello call get("/hello",
     * String.class).
     *
     * @param uri The endpoint to get the result of.
     * @param returnType The type of Object expected from the endpoint.
     * @return The result.
     */
    public <T> T get(String uri, Class<T> returnType) {
        return createResource(uri).get(returnType);
    }

    /**
     * Makes a POST request to a specified endpoint for a local API. E.g. to get
     * a String from the URL http://api.localhost/hello call post("/hello", "",
     * String.class).
     *
     * @param uri The endpoint to get the result of.
     * @param requestEntity The request body to post to the endpoint.
     * @param returnType The type of Object expected from the endpoint.
     * @return The result.
     */
    public <T> T post(String uri, Object requestEntity, Class<T> returnType) {
        return createResource(uri).post(returnType, requestEntity);
    }

    private Builder createResource(String uri) {
        // Append "api." to start of URL
        String apiUrl = request.getRequestURL().toString().replaceAll("://", "://api.");
        // Get a request builder object from API link (remove leading slash from parameter to prevent double slash)
        return Client.create().resource(apiUrl + uri.replaceAll("^/", "")).getRequestBuilder();
    }

}
