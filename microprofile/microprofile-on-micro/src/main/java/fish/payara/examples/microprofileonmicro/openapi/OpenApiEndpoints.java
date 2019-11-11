package fish.payara.examples.microprofileonmicro.openapi;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

/**
 *
 * @author Andrew Pielage <andrew.pielage@payara.fish>
 */
@ApplicationScoped
@Path("/OpenApi")
public class OpenApiEndpoints {
    
    @GET
    @Path("/Wobbly")
    @Operation(operationId = "Wibbly", summary = "Wibs", description = "Wibbles to and fro")
    public String wobbly() {
        return "Wibbly Wobbly!";
    }
    
    @GET
    @Path("Tiddles/{Toddles}")
    public String tiddles(@Parameter(description = "What does Tiddles do?", example = "Giggles") @PathParam("Toddles") String toddles) {
        return "Tiddles " + toddles + "!";
    }
}
