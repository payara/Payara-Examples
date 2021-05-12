package uk.me.mattgill.grpc.server.managed.api;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.inject.spi.CDI;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.grpc.BindableService;
import io.grpc.servlet.ServletAdapter;
import io.grpc.servlet.ServletAdapterBuilder;

public abstract class GrpcServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ServletAdapter adapter;

    public GrpcServlet(Class<?>... serviceClasses) {

        final Set<BindableService> services = new HashSet<>();

        for (Class<?> serviceClass : serviceClasses) {
            if (!BindableService.class.isAssignableFrom(serviceClass)) {
                throw new IllegalArgumentException("Unrecognised gRPC service: " + serviceClass);
            }
            BindableService service = (BindableService) CDI.current().select(serviceClass).get();
            services.add(service);
        }

        final ServletAdapterBuilder builder = new ServletAdapterBuilder();
        services.forEach(service -> builder.addService(service));

        this.adapter = builder.buildServletAdapter();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!ServletAdapter.isGrpc(req)) {
            resp.setContentType("text/plain");
            resp.getWriter().println("You should use gRPC to access this endpoint");
        } else {
            super.service(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        adapter.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        adapter.doPost(req, resp);
    }

    @Override
    public void destroy() {
        adapter.destroy();
        super.destroy();
    }

}
