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
import fish.payara.team.info.models.MemberList;

import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Fraser Savage
 */
@WebServlet(name = "SelectionServlet", urlPatterns = {"/select-member"})
public class SelectionServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(SelectionServlet.class.getCanonicalName());

    private Long Id;
    private String name;
    private String memberId = "memberEntity";
    private String listId = "memberList";

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
        name = request.getParameter("name");


        if (request.getParameter("name") == null) {
            try {
                Id = Long.parseLong(request.getParameter("id"));
            } catch (NumberFormatException e) {
                log.log(Level.INFO, "User placed an incorrect id in the input.");
                //Reloads the list page
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/memberlist.jsp");
                dispatcher.forward(request, response);
            } catch (NullPointerException e) {
                //Used to catch when selection servlet is called from the index page
            }
        }

        if (request.getParameter("id") != null) {
            MemberEntity member = bean.getTeamMemberById(Id);

            //Sets the attributes of the request to the object and a reference id for it.
            request.setAttribute(memberId, member);

            //Loads the management page
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/managemember.jsp");
            dispatcher.forward(request,response);
        }

        Collection<MemberEntity> membersWithName = bean.getTeamMemberByName(name);

        if (membersWithName.size() == 1) {
            MemberEntity member = bean.getTeamMemberById(membersWithName.iterator().next().getId());

            //Sets the attributes of the request to the object and a reference id for it.
            request.setAttribute(memberId, member);

            //Loads the management page
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/managemember.jsp");
            dispatcher.forward(request, response);
        } else if (membersWithName.size() < 1) {
            request.setAttribute("responseMessage","No team member with that name in the database.");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(request,response);
            log.log(Level.WARNING, "There is no user in the database with name, \"" + name + "\".");
        } else {
            MemberList memberList = new MemberList();

            String listItems = "";
            System.out.println(membersWithName.toString());
            for (MemberEntity m : membersWithName) {
                listItems = listItems + "<li> <b>ID:</b> " + m.getId() + ",     <b>Name:</b> " + m.getName() + ",  <b>Email:</b> " + m.getEmail() + "</li>\n";
            }

            memberList.setListBody("<ul>\n" + listItems + "</ul>\n");
            memberList.setName(name);

            System.out.println(memberList.getListOutput());

            //Sets the attributes of the request to the memberList object and gives it a reference id.
            request.setAttribute(listId, memberList);

            //Loads the list selection page
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(/* TODO List Selection Page */"/memberlist.jsp");
            dispatcher.forward(request, response);
        }
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
        processRequest(request,response);
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
