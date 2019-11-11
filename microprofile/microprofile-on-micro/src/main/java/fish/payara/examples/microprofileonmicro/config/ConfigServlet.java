package fish.payara.examples.microprofileonmicro.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Random;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.microprofile.config.Config;

/**
 *
 * @author Andrew Pielage <andrew.pielage@payara.fish>
 */
public class ConfigServlet extends HttpServlet {

    @Inject
    private ConfigExample configExample;
    
    @Inject
    private Config config;
    
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
            out.println("ConfigExample.getExampleProperty(): " + configExample.getExampleProperty());
            out.println("<br>ConfigExample.getAnotherExampleProperty(): " + configExample.getAnotherExampleProperty());
            
            out.println("<br>");
            
            out.println("<br>Config.getOptionalValue().orElse(): " + config
                    .getOptionalValue("fish.payara.examples.optional.config", String.class)
                    .orElse("Oh Noes!"));
            
            System.setProperty("fish.payara.examples.optional.config", "Oh Yeah!");
            
            out.println("<br>");
            
            System.setProperty("fish.payara.examples.config.sources", "Tiddles!");
            config.getConfigSources().forEach((configSource) ->
                    out.println("<br>Config Source " 
                    + configSource.getName() + ": "
                    + Optional.ofNullable(configSource.getValue("fish.payara.examples.config.sources"))
                            .orElse("Not Here!")));
            
            System.setProperty("fish.payara.examples.another.config.property", String.valueOf(new Random().nextInt()));
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

}
