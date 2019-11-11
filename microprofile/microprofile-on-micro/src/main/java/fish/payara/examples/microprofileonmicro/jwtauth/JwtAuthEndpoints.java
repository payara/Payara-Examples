package fish.payara.examples.microprofileonmicro.jwtauth;

import java.net.URI;
import java.net.URL;
import java.security.Principal;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author pandr
 */
@ApplicationScoped
@Path("/JwtAuth")
public class JwtAuthEndpoints {
    
    @Inject
    private Principal principal;
    
    @Inject
    private HttpServletRequest request;
    
    @GET
    @Path("/Access")
    public String accessProtected() throws Exception {
        return ClientBuilder.newClient().target(URI.create(
                new URL(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() 
                        + request.getContextPath() + "/JwtAuth/Protected")
                .toExternalForm()))
                .request(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.AUTHORIZATION, 
                        "Bearer " + JwtTokenGenerator.generateJwtString("jwt-token.json"))
                .get(String.class);
    }
    
    @GET
    @Path("/Protected")
    @RolesAllowed("payara")
    public String protectedResource() {
        return "Protected!";
    }
    
    @GET
    public String unprotectedResource() {
        return "Unprotected!";
    }
}
