/*

 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

 Copyright (c) 2015 C2B2 Consulting Limited. All rights reserved.

 The contents of this file are subject to the terms of the Common Development
 and Distribution License("CDDL") (collectively, the "License").  You
 may not use this file except in compliance with the License.  You can
 obtain a copy of the License at
 https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 or packager/legal/LICENSE.txt.  See the License for the specific
 language governing permissions and limitations under the License.

 When distributing the software, include this License Header Notice in each
 file and include the License file at packager/legal/LICENSE.txt.
 */
package fish.payara.examples.microprofile.configinjection;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.URL;
import java.time.LocalDate;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    
    // Example config property that uses a default converter to LocalDate
    @Inject
    @ConfigProperty(name = "date.property")
    LocalDate date;

    // Example injection the standard environment variable home
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
     * Note: this supported by Payara but isn't required to work in all MicroProfile Config implementations without a custom converter.
     */
    @Inject
    @ConfigProperty(name = "application.address", defaultValue = "10.0.0.1")
    InetAddress inetAddress;

    @Inject
    @ConfigProperty(name ="application.numberOfWorkers", defaultValue = "10")
    int numberOfWorkers;
    
    // Example injection of a request scoped bean that contains injected values. Configuration is updated for each request
    @Inject
    TestBean bean;
    

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

            out.println("<h2>Properties Injected into a Servlet");
            out.println("<p>These will only change on redeploy</p>");
            out.println("</h2>");
            out.println("<table><tr><th>Property Name</th><th>Property Value</th></tr>");
            printConfigProperty(out, "default.property", defaultProperty);
            printConfigProperty(out, "file.property",fileProperty);
            printConfigProperty(out, "date.property",date);
            printConfigProperty(out, "HOME",home);
            printConfigProperty(out, "java.home",javaHome);
            printConfigProperty(out, "application.property",applicationProperty);
            printConfigProperty(out, "fish.payara.examples.microprofile.configinjection.ShowConfigValues.url", url);
            printConfigProperty(out, "application.url", urlObject);
            printConfigProperty(out, "application.address", inetAddress.getHostAddress());
            printConfigProperty(out, "application.numberOfWorkers", numberOfWorkers);
            out.println("</table>");

            out.println("<h2>Properties Injected into a Request Scoped Bean");
            out.println("<p>These will change if you override them in Payara during runtime (e.g. in a cluster config source)</p>");
            out.println("</h2>");
            out.println("<table><tr><th>Property Name</th><th>Property Value</th></tr>");
            printConfigProperty(out, "default.property",bean.getDefaultProperty());
            printConfigProperty(out, "file.property",bean.getFileProperty());
            printConfigProperty(out, "date.property",bean.getDate());
            printConfigProperty(out, "HOME",bean.getHome());
            printConfigProperty(out, "java.home",bean.getJavaHome());
            printConfigProperty(out, "application.property",bean.getApplicationProperty());
            out.println("</table>");            
            out.println("</body>");
            out.println("</html>");
        }
    }

    private void printConfigProperty(final PrintWriter out, String propertyName, Object propertyValue) {
        out.format("<tr><td>%s</td><td>%s</td></tr>", propertyName, propertyValue);
    }

}
