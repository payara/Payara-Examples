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
package fish.payara.examples.payaramicro.event.sender;

import fish.payara.examples.payaramicro.eventdata.CustomMessage;
import fish.payara.micro.PayaraMicroRuntime;
import fish.payara.micro.cdi.ClusteredCDIEventBus;
import fish.payara.micro.cdi.Outbound;
import java.io.IOException;
import java.io.PrintWriter;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author steve
 */
@WebServlet(name = "SendEventServlet", urlPatterns = {"/SendEventServlet"}, loadOnStartup = 1)
public class SendEventServlet extends HttpServlet {
    
    // This is only needed to initialise the event bus
    @Inject
    ClusteredCDIEventBus bus;
   
    // Defines an Event Sender for "Outbound" CDI messages i.e. out of the server
    @Inject
    @Outbound
    Event<CustomMessage> event;
    
    @Inject
    PayaraMicroRuntime runtime;
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
            out.println("<title>Servlet SendEventServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SendEventServlet at " + request.getContextPath() + "</h1>");
            
            // use the CDI event object to fire CDI events
            String messageParam = request.getParameter("message");
            CustomMessage message = new CustomMessage(messageParam, runtime.getLocalDescriptor().getMemberUUID());
            event.fire(message);
            
            
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

    @Override
    public void init() throws ServletException {
        super.init(); 
        
        // you must intitialize the Event Bus in your servlet for events to flow.
        bus.initialize();

    }
    
    

}
