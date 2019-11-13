package fish.payara.examples.azure.k8s;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@WebServlet("/pod")
public class PodServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.getWriter().println(getServerInfo());
    }

    private String getServerInfo() throws UnknownHostException {
        return String.format("Executed From Server: %s", InetAddress.getLocalHost().getHostName());
    }


}
