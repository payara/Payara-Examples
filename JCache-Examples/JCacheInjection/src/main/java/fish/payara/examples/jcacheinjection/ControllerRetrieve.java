
package fish.payara.examples.jcacheinjection;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ahmed
 * Servlet class to handle the request for retrieving a value from the cache
 */
public class ControllerRetrieve extends HttpServlet {
    
    @Inject
    private Model m;

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
        String key = request.getParameter("idlastname");
        if(key.isEmpty()){
            request.setAttribute("message2", "Nothing to search for!");
        }
        else {
            String message = m.retrieveCache(key);
            if(message == null){
                request.setAttribute("message2", "Person does not exist in Cache");
            }
            else{
                request.setAttribute("message2", "You searched for "+ message);
            }
            
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request,response);
    }

}
