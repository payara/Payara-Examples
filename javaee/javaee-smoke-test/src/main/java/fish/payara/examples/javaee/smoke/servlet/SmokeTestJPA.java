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

import fish.payara.examples.javaee.smoke.cdi.RollbackException;
import fish.payara.examples.javaee.smoke.cdi.TransactionalCDI;
import fish.payara.examples.javaee.smoke.ejb.SmokeJPABMBean;
import fish.payara.examples.javaee.smoke.ejb.SmokeJPABean;
import fish.payara.examples.javaee.smoke.ejb.data.Person;
import fish.payara.examples.javaee.smoke.ejb.data.SmokeEntity;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Steve Millidge (Payara Foundation)
 */
@WebServlet(name = "SmokeTestJPA", urlPatterns = {"/SmokeTestJPA"})
public class SmokeTestJPA extends HttpServlet {
    
    @EJB
    SmokeJPABean ejb;
    
    @EJB
    SmokeJPABMBean bmejb;
    
    @Inject
    TransactionalCDI cdi;

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SmokeTestJPA</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>JPA Smoke tests</h1>");
            out.println("<table>");
            out.println("<tr><td>Persist and Retrieve EJB: </td><td>" + testInsertandRetrieve() + "</td></tr>");
            out.println("<tr><td>Delete All EJB: </td><td>" + testDeleteAll() + "</td></tr>");
            out.println("<tr><td>Persist and Retrieve CDI: </td><td>" + testInsertandRetrieveCDI() + "</td></tr>");
            out.println("<tr><td>Delete All CDI: </td><td>" + testDeleteAllCDI() + "</td></tr>");
            out.println("<tr><td>Persist and Retrieve EJB  BMT: </td><td>" + testInsertandRetrieveBMT()+ "</td></tr>");
            out.println("<tr><td>Delete All EJB BMT: </td><td>" + testDeleteAllBMT()+ "</td></tr>");
             out.println("<tr><td>Test Relations: </td><td>" + testRelations()+ "</td></tr>");
            out.println("<tr><td>Test Rollback: </td><td>" + testRollback()+ "</td></tr>");
            out.println("<tr><td>Test Rollback BMT: </td><td>" + testRollbackBMT()+ "</td></tr>");
            out.println("<tr><td>Test Rollback CDI: </td><td>" + testRollbackCDI()+ "</td></tr>");
            out.println("</table>");            
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    private boolean testInsertandRetrieve() {
        boolean result = false;
        SmokeEntity se = ejb.insert();
        SmokeEntity se2 = ejb.retrieve(se.getId());
        return Objects.equals(se.getId(), se2.getId());
    }
    
    private boolean testDeleteAll() {
        boolean result = false;
        ejb.bulkLoad(1000);
        result = ejb.countAll() >= 1000;
        ejb.deleteAll();
        result = result && (ejb.countAll() == 0);
        return result;
    }
    private boolean testInsertandRetrieveBMT() {
        boolean result = false;
        SmokeEntity se = bmejb.insert();
        SmokeEntity se2 = bmejb.retrieve(se.getId());
        return Objects.equals(se.getId(), se2.getId());
    }
    
    private boolean testDeleteAllBMT() {
        boolean result = false;
        bmejb.bulkLoad(1000);
        result = bmejb.countAll() >= 1000;
        bmejb.deleteAll();
        result = result && (bmejb.countAll() == 0);
        return result;
    }
    
        private boolean testInsertandRetrieveCDI() {
        boolean result = false;
        SmokeEntity se = cdi.insert();
        SmokeEntity se2 = cdi.retrieve(se.getId());
        return Objects.equals(se.getId(), se2.getId());
    }
    
    private boolean testDeleteAllCDI() {
        boolean result = false;
        cdi.bulkLoad(1000);
        result = cdi.countAll() >= 1000;
        cdi.deleteAll();
        result = result && (cdi.countAll() == 0);
        return result;
    }
    
    private boolean testRelations() {
        boolean result = false;
        Person fred = ejb.createFamily();
        fred = ejb.retrieveFamily(fred.getId());
        result = fred != null;
        result = result && (fred.getSpouse() != null);
        result = result && (fred.getSpouse().getSpouse().getId().equals(fred.getId()));
        result = result && (fred.getChildren().size() > 0);
        return result;
    }
    
    private boolean testRollback() {
        boolean result = false;
        ejb.deleteAll();
        ejb.rollBack();
        result = ejb.countAll() == 0;
        return result;
    }
    
    private boolean testRollbackBMT() {
        boolean result = false;
        bmejb.deleteAll();
        bmejb.rollBack();
        result = bmejb.countAll() == 0;
        return result;
    }
    
    private boolean testRollbackCDI() {
        boolean result = false;
        cdi.deleteAll();
        try {
            cdi.rollBack();
        } catch (RollbackException ex) {
        }
        result = cdi.countAll() == 0;
        return result;
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
