/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2017 Payara Foundation and/or its affiliates. All rights reserved.
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
package fish.payara.examples.microprofile.configinjection;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author steve
 */
@WebServlet(name = "ShowConfigValues", urlPatterns = {"/ShowConfigValues"})
public class ShowConfigValues extends HttpServlet {
    
    // Example showing injection of a default property
    @Inject
    @ConfigProperty(name = "default.property", defaultValue = "Default Value")
    String defaultProperty;
    
    // Example showing reading a config property from the META-INF/microprofile-config.properties file
    @Inject
    @ConfigProperty(name = "file.property")
    String fileProperty;

    // Example showing reading a config property from the META-INF/microprofile-config.properties file
    @Inject
    @ConfigProperty(name = "application.property")
    String applicationProperty;
    
    // Example injection of an optional value that's not required to be present
    @Inject
    @ConfigProperty(name = "application.optionalProperty")
    Optional<String> optionalApplicationProperty;
    
    // Example config property that uses a default converter to LocalDate
    @Inject
    @ConfigProperty(name = "date.property")
    LocalDate date;

    // Example injection of the standard environment variable home
    @Inject
    @ConfigProperty(name = "HOME", defaultValue = "HOME environment variable not set")
    String home;
    
    // Example injection of the standard System Property java.home
    @Inject
    @ConfigProperty(name = "java.home", defaultValue = "java.home environment variable not set")
    String javaHome;
    
    /* Example injection of a value with implicit name if "name" attribute isn't specified.
     * The implicit name of this property is "fish.payara.examples.microprofile.configinjection.ShowConfigValues.url".
     * If it's not specified, then the value in the "defaultValue" attribute applies.
     */
    @Inject
    @ConfigProperty(defaultValue = "http://localhost")
    String url;
    
    // Example injection of a URL object automatically converted from String
    @Inject
    @ConfigProperty(name = "application.url", defaultValue = "http://localhost")
    URL urlObject;
    
    /* Example injection of an InetAddress object automatically converted from String
     * Note: this is supported by Payara but isn't required to work in all MicroProfile Config implementations without a custom converter.
     */
    @Inject
    @ConfigProperty(name = "application.address", defaultValue = "10.0.0.1")
    InetAddress inetAddress;

    // Example injection of a primitive type
    @Inject
    @ConfigProperty(name ="application.numberOfWorkers", defaultValue = "10")
    int numberOfWorkers;
    
    // Example injection of a request scoped bean that contains injected values. Configuration is updated for each request
    @Inject
    TestBean bean;
    
    // Example injection of the default configuration singleton instance which provides lower level programmatic API to retireve configuration values
    @Inject
    Config config;
    
    // Example showing reading a config property dynamically using Provider
    @Inject
    @ConfigProperty(name = "application.dynamicProperty", defaultValue = "Default Value")
    Provider<String> dynamicProperty;
    
    /* Example of injecting a value of an arbitrary type using a custom converter. 
     * TestPojo types are converted using CustomConverter,java, which is registered via a service locator file
     */
    @Inject
    @ConfigProperty(name = "pojo.value")
    TestPojo pojo;
    
    @Inject
    @ConfigProperty(name = "echo.property", defaultValue = "Provided if EchoConfigSource disabled")
    String propertyFromEchoConfigSource;
      
    /* An example of injecting a parameterized type using a custom converter 
     * (ConfigListConverter.java registered via a service locator file)
     */
//    @Inject
//    @ConfigProperty(name = "list")
    List<String> injectedList;


    // Example showing reading an optional config property dynamically using Provider
    @Inject
    @ConfigProperty(name = "application.dynamicOptionalProperty")
    Provider<Optional<String>> dynamicOptionalProperty;
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            printHeader(out);

            printStaticInjectedPropeties(out);

            printRequestScopedInjectedProperties(out);            
                        
            printPayaraProperties(out);
            
//            printListOfProperties(out);

            printPropertiesRetrievedProgrammatically(out);

            printDynamicInjectedProperties(out);

            printFooter(out);
        }
    }

    private void printFooter(final PrintWriter out) {
        out.println("</body>");
        out.println("</html>");
    }

    private void printDynamicInjectedProperties(final PrintWriter out) {
        out.println("<h2>Properties retrieved dynamically using a provider");
        out.println("<p>These will change immediately if you override them in Payara during runtime</p>");
        out.println("</h2>");
        out.println("<table><tr><th>Property Name</th><th>Property Value</th></tr>");
        printConfigProperty(out, "application.dynamicProperty", dynamicProperty.get());
        printConfigProperty(out, "application.dynamicOptionalProperty", dynamicOptionalProperty.get().orElse("Not defined"));
        out.println("</table>");
    }

    private void printPropertiesRetrievedProgrammatically(final PrintWriter out) throws UnknownHostException, MalformedURLException {
        out.println("<h2>Properties retrieved from an injected Config instance programmatically");
        out.println("<p>These will change immediately if you override them in Payara during runtime</p>");
        out.println("</h2>");
        out.println("<table><tr><th>Property Name</th><th>Property Value</th></tr>");
        printConfigProperty(out, "default.property", config
                .getOptionalValue("default.property", String.class)
                .orElse("Default value"));
        printConfigProperty(out, "file.property", config
                .getValue("file.property", String.class));
        printConfigProperty(out, "date.property", config
                .getValue("date.property", LocalDate.class));
        printConfigProperty(out, "HOME", config
                .getOptionalValue("HOME", String.class)
                .orElse("HOME environment variable not set"));
        printConfigProperty(out, "java.home", config
                .getOptionalValue("java.home", String.class)
                .orElse("java.home environment variable not set"));
        printConfigProperty(out, "application.property", config
                .getValue("application.property", String.class));
        printConfigProperty(out, "application.url", config
                .getOptionalValue("application.url", URL.class)
                .orElse(new URL("http://localhost")));
        printConfigProperty(out, "application.address", config
                .getOptionalValue("application.address", InetAddress.class)
                .orElse(InetAddress.getByAddress(new byte[] {10, 0, 0, 1}))
                .getHostAddress());
        printConfigProperty(out, "application.numberOfWorkers", config
                .getOptionalValue("application.numberOfWorkers", Integer.class)
                .orElse(10));
        out.println("</table>");
    }

    private void printListOfProperties(final PrintWriter out) {
        out.println("<h2>SInjected list using a custom converter</h2>");
        out.println("<table><tr><th>Element Number</th><th>Value</th></tr>");
        int elementCount = 0;
        for (String string : injectedList) {
            out.println("<tr><td>"+ elementCount++ +"</td><td>" + string + "</td></tr>");
        }
        out.println("</table>");
    }

    private void printPayaraProperties(final PrintWriter out) {
        out.println("<h2>Standard Payara config properties");
        out.println("<p>These will be provided automatically by the server via the config API</p>");
        out.println("</h2>");
        out.println("<table><tr><th>Property Name</th><th>Property Value</th></tr>");
        printConfigProperty(out, "payara.instance.http.port", config.getValue("payara.instance.http.port", Integer.class));
        printConfigProperty(out, "payara.instance.http.address", config.getValue("payara.instance.http.address", InetAddress.class));
        printConfigProperty(out, "payara.instance.https.port", config.getValue("payara.instance.https.port", Integer.class));
        printConfigProperty(out, "payara.instance.https.address", config.getValue("payara.instance.https.address", InetAddress.class));
        printConfigProperty(out, "payara.instance.root", config.getValue("payara.instance.root", String.class));
        printConfigProperty(out, "payara.instance.type", config.getValue("payara.instance.type", String.class));
        printConfigProperty(out, "payara.instance.name", config.getValue("payara.instance.name", String.class));
        printConfigProperty(out, "payara.domain.name", config.getValue("payara.domain.name", String.class));
        printConfigProperty(out, "payara.domain.installroot", config.getValue("payara.domain.installroot", String.class));
        printConfigProperty(out, "payara.config.dir", config.getValue("payara.config.dir", String.class));
        printConfigProperty(out, "payara.instance.config.name", config.getValue("payara.instance.config.name", String.class));
        printConfigProperty(out, "payara.instance.admin.host", config.getValue("payara.instance.admin.host", String.class));
        printConfigProperty(out, "payara.instance.admin.port", config.getValue("payara.instance.admin.port", Integer.class));
        printConfigProperty(out, "payara.instance.starttime", config.getValue("payara.instance.starttime", Long.class));
        out.println("</table>");
    }

    private void printRequestScopedInjectedProperties(final PrintWriter out) {
        out.println("<h2>Properties injected into a Request Scoped Bean");
        out.println("<p>These will change in a new request if you override them in Payara during runtime (e.g. in a server or a cluster config source)</p>");
        out.println("</h2>");
        out.println("<table><tr><th>Property Name</th><th>Property Value</th></tr>");
        printConfigProperty(out, "default.property",bean.getDefaultProperty());
        printConfigProperty(out, "file.property",bean.getFileProperty());
        printConfigProperty(out, "date.property",bean.getDate());
        printConfigProperty(out, "HOME",bean.getHome());
        printConfigProperty(out, "java.home",bean.getJavaHome());
        printConfigProperty(out, "application.property",bean.getApplicationProperty());
        printConfigProperty(out, "Test Pojo", pojo);
        out.println("</table>");
    }

    private void printStaticInjectedPropeties(final PrintWriter out) {
        out.println("<h2>Properties injected into a Servlet");
        out.println("<p>These will only change on redeploy</p>");
        out.println("</h2>");
        out.println("<table><tr><th>Property Name</th><th>Property Value</th></tr>");
        printConfigProperty(out, "default.property", defaultProperty);
        printConfigProperty(out, "file.property",fileProperty);
        printConfigProperty(out, "date.property",date);
        printConfigProperty(out, "HOME",home);
        printConfigProperty(out, "java.home",javaHome);
        printConfigProperty(out, "application.property",applicationProperty);
        printConfigProperty(out, "application.optionalProperty", optionalApplicationProperty.orElse("Not defined"));
        printConfigProperty(out, "fish.payara.examples.microprofile.configinjection.ShowConfigValues.url", url);
        printConfigProperty(out, "application.url", urlObject);
        printConfigProperty(out, "application.address", inetAddress.getHostAddress());
        printConfigProperty(out, "application.numberOfWorkers", numberOfWorkers);
        printConfigProperty(out, "echo.property", propertyFromEchoConfigSource);
        out.println("</table>");
    }

    private void printHeader(final PrintWriter out) {
        /* Output a sample page with values retrieved from the configuration */
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet Demonstrating use of the Microprofile Config API for Injection of Configuration Values</title>");
        out.println("<style>"
                + "h2 p { font-size: 70%; margin-left: 3em; font-style: italic; font-weight: normal; margin-top: 0.2em; }"
                + "table { border-collapse: collapse; }"
                + "td, th { border: 1px solid black; padding: 0.2em 1em; }"
                + "</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servlet Demonstrating use of the Microprofile Config API for Injection of Configuration Values</h1>");
        out.println("<p>Configuration values shown here can be overriden in the administration console in Domain, Instance and Application Properties</p>");
        out.println("<p>You can also override values by setting system properties or environment variables</p>");
    }

    private void printConfigProperty(final PrintWriter out, String propertyName, Object propertyValue) {
        out.format("<tr><td>%s</td><td>%s</td></tr>", propertyName, propertyValue);
    }

}
