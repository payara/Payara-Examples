package fish.payara.team.info.controllers;

import fish.payara.team.info.models.MemberEntity;
import fish.payara.team.info.models.MemberList;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
