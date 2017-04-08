/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2016 Payara Foundation and/or its affiliates. All rights reserved.
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
package fish.payara.examples.javaee.smoke.servlet;

import fish.payara.examples.javaee.smoke.cdi.ApplicationScopedBean;
import fish.payara.examples.javaee.smoke.cdi.EJBInjectionTester;
import fish.payara.examples.javaee.smoke.cdi.POJO;
import fish.payara.examples.javaee.smoke.cdi.RequestScopedBean;
import fish.payara.examples.javaee.smoke.cdi.SessionScopedBean;
import fish.payara.examples.javaee.smoke.ejb.TestSessionBean;
import fish.payara.examples.javaee.smoke.ejb.TestSingletonBean;
import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

/**
 *
 * @author Steve Millidge (Payara Foundation)
 */
@WebServlet(name = "TestServlet", urlPatterns = {"/TestServlet"}, initParams = {
    @WebInitParam(name = "Init1", value = "Test")})
public class TestServlet extends HttpServlet {

    private boolean initPass1 = false;
    private boolean initPass2 = false;
    
    @EJB
    TestSessionBean bean;
    
    @EJB
    TestSingletonBean singleton;
    
    @Inject
    RequestScopedBean requestBean;
    
    @Inject
    SessionScopedBean sessionBean;
    
    @Inject
    ApplicationScopedBean appbean;
    
    @Inject
    EJBInjectionTester ejbCDIBean;
    
    @Inject
    Boolean value;
    
    @Inject
    POJO pojo;
    
    @Inject
    Event<Integer> countEvent;
    
    @Resource
    ManagedExecutorService executor;
    
    @Resource
    UserTransaction transaction;
    
    @Resource(name="java:comp/DefaultDataSource")
    private javax.sql.DataSource dsc;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config); 
        initPass1 = "Test".equals(config.getInitParameter("Init1"));
        initPass2 = "Test2".equals(config.getServletContext().getInitParameter("Init2"));
    }

    
    
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
        
        HttpSession session = request.getSession(true);
        Integer requestCount = (Integer) session.getAttribute("RequestCount");
        if (requestCount == null) {
            requestCount = 0;
        }
        
        boolean appBeanSameCount = (requestCount == appbean.getStoredValue());
        boolean singletonSameCount = (requestCount == singleton.getStoredValue());
        
        // test update CDI and EJB via CDI Events
        countEvent.fire(requestCount);
        boolean eventCountTest = appbean.getEventValue() == requestCount;
        boolean ejbEventtest = singleton.getEventValue() == requestCount;
        
        // update the session
        requestCount = requestCount + 1;
        session.setAttribute("RequestCount", requestCount);
        appbean.setStoredValue(requestCount);
        singleton.setStoredValue(requestCount);
        
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Basic Injection Tests</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Basic Injection Tests</h1>");
            out.println("<table>");
            out.println("<tr><td>Init Parameter 1: </td><td>" + initPass1 + "</td></tr>");
            out.println("<tr><td>Init Parameter 2: </td><td>" + initPass2 + "</td></tr>");
            out.println("<tr><td>Filter Attribute: </td><td>" + "Test".equals(request.getAttribute("Filter1"))+ "</td></tr>");
            out.println("<tr><td>Filter Init: </td><td>" + "Test".equals(request.getAttribute("Filter2"))+ "</td></tr>");
            out.println("<tr><td>Filter CDI AS Injection </td><td>" + request.getAttribute("FilterAppInject")+ "</td></tr>");
            out.println("<tr><td>Filter CDI SS Injection </td><td>" + request.getAttribute("FilterSInject")+ "</td></tr>");
            out.println("<tr><td>Filter CDI RS Injection </td><td>" + request.getAttribute("FilterRInject")+ "</td></tr>");
            out.println("<tr><td>Filter EJB Injection </td><td>" + request.getAttribute("FilterEJBInject")+ "</td></tr>");
            out.println("<tr><td>Servlet EJB Injection: </td><td>" + bean.doYouExist()+ "</td></tr>");
            out.println("<tr><td>Servlet CDI RS Injection: </td><td>" + requestBean.doIExist()+ "</td></tr>");
            out.println("<tr><td>Servlet POJO Injection: </td><td>" + pojo.doIExist()+ "</td></tr>");
            out.println("<tr><td>Servlet CDI RS Stored Value Reset: </td><td>" + requestBean.isStoredValueReset()+ "</td></tr>");
            out.println("<tr><td>Servlet CDI SS Injection: </td><td>" + sessionBean.doIExist()+ "</td></tr>");
            out.println("<tr><td>Servlet CDI SS Stored Value Count: </td><td>" + requestCount.equals(sessionBean.incrementValue())+ "</td><td> Value is " + sessionBean.getValue() + "</td></tr>");
            out.println("<tr><td>Servlet CDI AS Stored Value Count: </td><td>" + appBeanSameCount + "</td><td> Value is " + appbean.getStoredValue() + "</td></tr>");
            out.println("<tr><td>EJB CDI AS Injection: </td><td>" + bean.testAppInjection()+ "</td></tr>");
            out.println("<tr><td>EJB CDI SS Injection: </td><td>" + bean.testSInjection()+ "</td></tr>");
            out.println("<tr><td>EJB CDI RS Injection: </td><td>" + bean.testRInjection()+ "</td></tr>");
            out.println("<tr><td>CDI EJB Injection: </td><td>" + ejbCDIBean.doesEJBExist() + "</td></tr>");
            out.println("<tr><td>CDI Interceptor: </td><td>" + appbean.returnFalse() + "</td></tr>");
            out.println("<tr><td>CDI Produces Boolean: </td><td>" + value + "</td></tr>");
            out.println("<tr><td>CDI Eventing: </td><td>" + eventCountTest + "</td></tr>");
            out.println("<tr><td>CDI Eventing in EJB: </td><td>" + ejbEventtest + "</td></tr>");
            out.println("<tr><td>Servlet Singleton EJB Stored Value Count: </td><td>" + singletonSameCount + "</td><td> Value is " + singleton.getStoredValue() + "</td></tr>");
            out.println("<tr><td>Singleton EJB CDI Interceptor: </td><td>" + singleton.returnFalse() + "</td></tr>");
            out.println("<tr><td>Executor Injection: </td><td>" + (executor != null) + "</td></tr>");
            out.println("<tr><td>UserTransaction Injection: </td><td>" + (transaction != null) + "</td></tr>");
            out.println("<tr><td>Default DS Injection: </td><td>" + (dsc != null) + "</td></tr>");
            out.println("</table>");            
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

}
