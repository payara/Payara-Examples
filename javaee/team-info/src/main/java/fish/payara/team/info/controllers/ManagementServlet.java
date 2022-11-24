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
package fish.payara.team.info.controllers;

import fish.payara.team.info.models.MemberEntity;

import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Fraser Savage
 */
@WebServlet(name = "ManagementServlet", urlPatterns = {"/manage-member"})
public class ManagementServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(ManagementServlet.class.getCanonicalName());

    private Long Id;
    private String name;
    private String email;
    private String bio;
    private String memberId = "memberEntity";

    @EJB
    MemberSessionBean bean;

    /**
     * Processes <code>GET</code> and <code>POST</code>
     * @param request - servlet request
     * @param response - servlet response
     * @throws ServletException - if a servlet error occurs
     * @throws IOException - if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Id = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e) {
            log.log(Level.INFO, "Could not parse the Id to long, for parameter value: " + request.getParameter("id"));
        }

        name = request.getParameter("name");
        email = request.getParameter("email");
        bio = request.getParameter("bio");

        MemberEntity member = bean.getTeamMemberById(Id);

        if (member.getName().equals(name) == false) {
            String previousName = member.getName();
            bean.updateTeamMemberName(Id, name);
            log.log(Level.INFO, "Updated the name of team member \"" + previousName + "\" to \"" + member.getName() + "\".");
        }
        System.out.println(email);
        if (member.getEmail().equals(email) == false) {
            String previousEmail = member.getEmail();
            bean.updateTeamMemberEmail(Id, email);
            log.log(Level.INFO, "Updated the email of team member \"" + previousEmail + "\" to \"" + member.getEmail() + "\".");
        }
        if (member.getBio().equals(bio) == false) {
            String previousBio = member.getBio();
            bean.updateTeamMemberBio(Id,bio);
            log.log(Level.INFO, "Updated the bio of team member \"" + previousBio + "\" to \"" + member.getBio() + "\".");
        }

        member = bean.getTeamMemberById(Id);


        //Set member object and its bean identifier as attributes of the http request.
        request.setAttribute(memberId,member);

        //Loads the manage member page with updated values.
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/managemember.jsp");
        dispatcher.forward(request, response);

    }

    /**
     * Handles HTTP <code>GET</code> method.
     * @param request - servlet request
     * @param response - servlet response
     * @throws ServletException - if a servlet error occurs
     * @throws IOException - if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles HTTP <code>POST</code> method.
     * @param request - servlet request
     * @param response - servlet response
     * @throws ServletException - if a servlet error occurs
     * @throws IOException - if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
