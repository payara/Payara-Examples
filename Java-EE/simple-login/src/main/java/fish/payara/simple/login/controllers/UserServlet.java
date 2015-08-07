package fish.payara.simple.login.controllers;

import fish.payara.simple.login.models.UserBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Fraser Savage
 * This handles user profile updates.
 */
@WebServlet(name = "UserServlet", urlPatterns = "/dashboard-update")
public class UserServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(UserServlet.class.getCanonicalName());
    private static final String table = "usertable";

    private String username;
    private String bio;
    private String userID = "userBean";

    /**
     * Returns the SQL statement used to update the user's bio field.
     * @param username - The user to update the bio for.
     * @param bio - The value to update the bio to.
     * @return
     */
    private String createUpdateBio(String username, String bio) {
        return ("UPDATE " + table + " SET bio='" + bio + "' WHERE username='" + username +"';" );
    }

    /**
     * Returns the SQL statement used to get all the values for a given username.
     * @param username - Desired user to retrieve information for.
     * @return
     */
    private String getUserDetail(String username) {
        return ("SELECT * FROM " + table + "  WHERE username='" + username + "';");
    }

    /**
     * Processes requests for HTTP <code>POST</code> method.
     * @param request - Servlet Request
     * @param response - Servlet Response
     * @throws ServletException - If Servlet errors happen.
     * @throws IOException - If I/O errors occur.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean successful = true;

        username = request.getParameter("username");
        bio = request.getParameter("bio");

        try(Connection connection = GetConnection.getJNDIConnection();
            Statement SQLStatement = connection.createStatement()) {

            try {
                SQLStatement.execute(createUpdateBio(username,bio));
            } catch (SQLException e) {
                log.log(Level.WARNING, "Could not update bio for user \"" + username + "\". Exception: " + e);
                successful = false;
            }

            UserBean userBean = new UserBean();

            try {
                ResultSet rs = SQLStatement.executeQuery(getUserDetail(username));
                rs.next();

                userBean.setUsername(username);
                userBean.setDateOfBirth(rs.getString("dateOfBirth"));
                userBean.setDateOfRegister(rs.getString("dateOfRegister"));
                userBean.setBio(rs.getString("bio"));


            } catch (SQLException e) {
                log.log(Level.WARNING, "Could not get details of \"" + username + "\". Exception: " + e);
                successful = false;
            }

            if (successful == true) {
                //Set the user object and the objectID as attributes of the http request.
                request.setAttribute(userID, userBean);

                //Loads the register response page.
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/loginLandingPage.jsp");
                dispatcher.forward(request, response);
            }

        } catch (SQLException e) {
            log.log(Level.SEVERE, "Could not create SQL statement, exception: " + e);
        }
    }

    /**
     * A post request runs the process request method with the servlet request and response.
     * @param request - Servlet Request
     * @param response - Servlet Response
     * @throws ServletException - If a Servlet error occurs.
     * @throws IOException - If an I/O error occurs.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            processRequest(request,response);
    }
}
