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
package fish.payara.examples.microprofile.faulttolerance;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mike Croft
 */

@Path("employees")
@ApplicationScoped
public class EmployeeResource {

    private final long TIMEOUT = 500;
    private final long SLEEPTIME = 1000;
    private long retryCounter = 0;

    private List<String> employees = Arrays.asList(
            "John",
            "Paul",
            "George",
            "Ringo");

    @GET
    @Path("{id}")
    @Retry(maxRetries = 4, retryOn = {RuntimeException.class})
    public String getEmployeeById(@PathParam("id") int id) {
        System.out.println("Called getEmployeeById a total of " + ++retryCounter + " times");
        if (id >= employees.size()) return "No such employee. Try a number lower than " + employees.size();
        if (isDown()) throw new RuntimeException();
        return employees.get(id);
    }

    private boolean isDown() {
        // approx 80% chance
        return Math.random() > 0.2;
    }

    @GET
    @Fallback(fallbackMethod = "getAllEmployeesFallback")
    @Timeout(TIMEOUT)
    public String getAllEmployees() throws InterruptedException {
        if (isSlow()) return employees.toString();
        return employees.toString();
    }

    public String getAllEmployeesFallback() {
        return "It took longer than expected to get all employees. Try again later!";
    }

    private boolean isSlow() throws InterruptedException {
        if (Math.random() > 0.4) {
            // approx 60% chance
            Thread.sleep(SLEEPTIME);
            return true;
        }
        return false;
    }
}
