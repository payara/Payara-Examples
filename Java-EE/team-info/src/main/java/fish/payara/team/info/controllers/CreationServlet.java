package fish.payara.team.info.controllers;

import fish.payara.team.info.models.MemberEntity;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Fraser Savage
 */
@WebServlet(name = "CreationServlet", urlPatterns = {"/add-member"})
public class CreationServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(CreationServlet.class.getCanonicalName());

    private String name;
    private String dateOfBirth;
    private String dateOfHire;
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

        MemberEntity member = new MemberEntity();

        try {
            member.setName(request.getParameter("name"));
        } catch (ValidationException e) {
            log.log(Level.WARNING, "Name to be set was not valid.");
        }

        member.setDateOfBirth(request.getParameter("dateOfBirth"));
        member.setDateOfHire(request.getParameter("dateOfHire"));

        try {
            member.setEmail(request.getParameter("email"));
        } catch (ValidationException e) {
            log.log(Level.WARNING, "Email to be set was not valid.");
        }

        try {
            member.setBio(request.getParameter("bio"));
        } catch (ValidationException e) {
            log.log(Level.WARNING, "Bio to be set was not valid.");
        }


        bean.createTeamMember(member);

        //Sets the member entity and it's identifier as attributes of the request.
        request.setAttribute(memberId, member);

        //Loads the add response page.
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/addresponse.jsp");
        dispatcher.forward(request,response);

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
        processRequest(request,response);
    }
}
