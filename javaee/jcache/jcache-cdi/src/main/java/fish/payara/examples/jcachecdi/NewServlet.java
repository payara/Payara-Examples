
package fish.payara.examples.jcachecdi;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ahmed
 * Servlet to handle the request to generate some text
 */
@WebServlet("/NewServlet")
public class NewServlet extends HttpServlet {

    private int number;
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
        
        number = Integer.parseInt(request.getParameter("number"));
        long startTime = System.currentTimeMillis();
        String random = m.slowOperation(number);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        request.setAttribute("result", "<div>" + random + " - that took " + duration + " ms" + "</div>");       
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request,response);
        
    }

}
