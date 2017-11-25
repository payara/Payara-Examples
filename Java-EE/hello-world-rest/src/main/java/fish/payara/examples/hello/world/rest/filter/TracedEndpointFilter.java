package fish.payara.examples.hello.world.rest.filter;

import fish.payara.examples.hello.world.rest.filter.annotations.TracedEndpoint;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

@TracedEndpoint
@Provider
public class TracedEndpointFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        Logger.getLogger(TracedEndpoint.class.getName()).log(Level.INFO, "{0} -> {1}", new Object[]{requestContext.getMethod(), requestContext.getUriInfo().getPath()});
    }

}
