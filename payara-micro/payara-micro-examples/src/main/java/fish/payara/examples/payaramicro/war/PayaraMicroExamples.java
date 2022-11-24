/*
 * DO NOT ALTER OR REMOTE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2015-2022 Payara Foundation and/or its affiliates. All rights reserved.
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
package fish.payara.examples.payaramicro.war;

import com.hazelcast.core.DistributedObject;
import com.hazelcast.core.HazelcastInstance;
import fish.payara.cdi.jsr107.impl.NamedCache;
import fish.payara.micro.PayaraMicroRuntime;
import fish.payara.micro.data.ApplicationDescriptor;
import fish.payara.micro.data.InstanceDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import javax.cache.Cache;
import javax.cache.CacheManager;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author steve
 */
@WebServlet(name = "PayaraMicroExamples", urlPatterns = {"/PayaraMicroExamples"})
public class PayaraMicroExamples extends HttpServlet {
    
   @Inject
   PayaraMicroRuntime runtime;
   
   @Inject
   HazelcastInstance instance;
   
   @Inject
   CacheManager cm;
   
   @NamedCache (cacheName = "Payara Examples JCache")
   @Inject Cache examplesCache;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
       // let us initialise the session
        HttpSession session = request.getSession(true);
        Long requestCount = (Long) session.getAttribute("RequestCount");
        if (requestCount == null) {
            requestCount = 0L;
        }
        session.setAttribute("RequestCount", requestCount+1);
        
        // Store stuff in JSR 107 JCache
        Long cacheCount = (Long) examplesCache.get("RequestCount");
        if (cacheCount == null) {
            cacheCount = 0L;
        }
        examplesCache.put("RequestCount", cacheCount+1);
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PayaraMicroExamples</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PayaraMicroExamples at " + request.getContextPath() + "</h1>");
            out.println("Cluster-wide HTTP Session request count : " + session.getAttribute("RequestCount"));
            out.println("<br/>JCache stored request count : " + examplesCache.get("RequestCount"));
            
            
            
            out.println("<h2>Running Payara Micro Nodes</h2><table>");
            out.println("<tr><th>Name</th><th>UUID</th><th>Host</th><th>HTTP Port</th><th>Deployed Applications</th></tr>");
            Collection<InstanceDescriptor> clusteredPayaras = runtime.getClusteredPayaras();
            for (InstanceDescriptor clusteredPayara : clusteredPayaras) {
                out.println("<tr><td>" + clusteredPayara.getInstanceName()+"</td>"
                + "<td>" + clusteredPayara.getMemberUUID() + "</td>"
                + "<td>" + clusteredPayara.getHostName().toString() + "</td>"
                + "<td>" + clusteredPayara.getHttpPorts().get(0) + "</td>");
                out.println("<td><table>");
                for (ApplicationDescriptor app :  clusteredPayara.getDeployedApplications()) {
                    out.println("<tr><td>" + app.getName() +"</td></tr>");
                }
                out.println("</table></td></tr>");
            }
            out.println("</table>");
            
            out.println("<h2>Distributed Objects</h2><table>");
            out.println(getCacheDescriptions());
            
            
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    
    private String getCacheDescriptions() {
        StringBuilder result = new StringBuilder();
        for (DistributedObject dObject : instance.getDistributedObjects()) {
            result.append("HZ Object : ").append(dObject.getName()).append("<br/>");
        }
        
        for (String name : cm.getCacheNames()) {
            result.append("JCache : ").append(name).append("<br/>");            
        }
       return result.toString();
        
    }
}
