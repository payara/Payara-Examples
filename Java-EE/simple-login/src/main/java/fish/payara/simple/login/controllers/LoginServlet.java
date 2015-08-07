package fish.payara.simple.login.controllers;

import fish.payara.simple.login.models.UserBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Fraser Savage
 * This handles the login request.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/dashboard"})
public class LoginServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(RegisterServlet.class.getCanonicalName());
    private static final String table = "usertable";

    private String username;
    private String password;
    private String userID = "userBean";

    /**
     * Gets all column values for a given username.
     * @param username
     * @return
     */
    private String getUserDetail(String username) {
        return ("SELECT * FROM " + table + "  WHERE username='" + username + "';");
    }

    /**
     * Processes requests for HTTP <code>POST</code> method.
     * @param request - Servlet Request.
     * @param response - Servlet Response.
     * @throws ServletException - If a servlet error occurs.
     * @throws IOException - If an I/O errors occurs.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        username = request.getParameter("username");
        password = request.getParameter("password");

        UserBean userBean = new UserBean();

        try(Connection connection = GetConnection.getJNDIConnection();
            Statement SQLStatement = connection.createStatement()) {

            //Verifies passwords match and then populates the userBean.
            try {
                ResultSet rs = SQLStatement.executeQuery(getUserDetail(username));
                rs.next();
                String storedPassword = rs.getString("password");

                if (password.equals(storedPassword)) {
                    userBean.setUsername(username);
                    userBean.setDateOfBirth(rs.getString("dateOfBirth"));
                    userBean.setDateOfRegister(rs.getString("dateOfRegister"));
                    userBean.setBio(rs.getString("bio"));
                } else {
                    log.log(Level.WARNING, "Attempted login with an incorrect password for \"" + username + "\".");
                    RequestDispatcher userExistsDispatcher = getServletContext().getRequestDispatcher("/index.jsp");
                    userExistsDispatcher.forward(request, response);
                }
            } catch (SQLException e) {
                log.log(Level.WARNING, "Attempted login when no username of \"" + username + "\" exists. Exception: " + e);
                RequestDispatcher userExistsDispatcher = getServletContext().getRequestDispatcher("/index.jsp");
                userExistsDispatcher.forward(request, response);
            }

            //Set the user object and the objectID as attributes of the http request.
            request.setAttribute(userID, userBean);

            //Loads the register response page.
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/loginLandingPage.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            log.log(Level.SEVERE, "Could not dispatch request, exception: " + e);
        }
    }

    /**
     * A post request runs the process request method with the servlet request and response.
     * @param request - Servlet Request
     * @param response - Servlet Response
     * @throws ServletException - If a Servlet error occurs.
     * @throws IOException - If an I/O error occurs.
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
