package fish.payara.simple.login.controllers;

import javax.inject.Inject;
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
import java.util.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import fish.payara.simple.login.models.UserBean;

/**
 * @author Fraser Savage
 * This handles the register new user request.
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/registered"})
public class RegisterServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(RegisterServlet.class.getCanonicalName());
    private static final String table = "usertable";

    private String username;
    private String password;
    private String dateOfBirth;
    private String dateOfRegister;
    private String userID = "userBean";

    /**
     * Returns the SQL statement used to create a new user in the database.
     * @param username - The unique username to create.
     * @param password - The password for the user.
     * @param dateOfBirth - The date of birth provided by the user.
     * @param dateOfRegister - Today's date in string format.
     * @return
     */
    private String createRegisterStatement(String username, String password, String dateOfBirth, String dateOfRegister) {
        return ("INSERT INTO " + table + " (username,password,dateOfBirth,dateOfRegister) VALUES ('" + username + "','" + password + "','" + dateOfBirth + "','" + dateOfRegister + "');");
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
        dateOfBirth = request.getParameter("dateOfBirth");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateOfRegister = dateFormat.format(new Date());

        UserBean userBean = new UserBean();

        userBean.setUsername(username);
        userBean.setPassword(password);
        userBean.setDateOfBirth(dateOfBirth);

        try(Connection connection = GetConnection.getJNDIConnection();
            Statement SQLStatement = connection.createStatement()) {

            //
            try {
                DatabaseMetaData metaData = connection.getMetaData();
                ResultSet resultSet = metaData.getTables(null, null, table, new String[]{"TABLE"});

                boolean tablePresent = false;

                while(resultSet.next()) {
                    if (resultSet.getString("TABLE_NAME") == table) {
                        tablePresent = true;
                        break;
                    }
                }

                if (tablePresent != true) {
                    SQLStatement.execute("CREATE TABLE " + table
                            + "("
                            + " username varchar(25),"
                            + " password varchar(25),"
                            + " dateOfBirth varchar(14),"
                            + " dateOfRegister varchar(14),"
                            + " bio varchar(255),"
                            + " PRIMARY KEY (username)"
                            + ");");
                }
            } catch (SQLException e) {
                log.log(Level.SEVERE, "SQL Exception: " + e);
            }

            //Attempts to create a new user.
            try {
                SQLStatement.execute(createRegisterStatement(username,password,dateOfBirth,dateOfRegister));
            } catch (SQLException e) {
                log.log(Level.WARNING, "User tried to register user with an existing username. Exception: " + e);
                RequestDispatcher userExistsDispatcher = getServletContext().getRequestDispatcher("/register.jsp");
                userExistsDispatcher.forward(request, response);
            }

            //Set the user object and the objectID as attributes of the http request.
            userBean.setPassword("");
            request.setAttribute(userID, userBean);

            //Loads the register response page.
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/registerResponse.jsp");
            dispatcher.forward(request,response);

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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
