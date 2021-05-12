package uk.me.mattgill.grpc.server.managed;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.grpc.servlet.ServletAdapter;
import uk.me.mattgill.grpc.server.managed.api.GrpcServlet;

@WebServlet(value = "/uk.me.mattgill.grpc.MyServices/notify", asyncSupported = true)
public class MyServlet extends GrpcServlet {

    private static final long serialVersionUID = 1L;

    public MyServlet() {
        super(MyEjbService.class);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!ServletAdapter.isGrpc(req)) {
            resp.setContentType("text/plain");
            resp.getWriter().println("Hello non-gRPC client!");
        } else {
            super.service(req, resp);
        }
    }

}
