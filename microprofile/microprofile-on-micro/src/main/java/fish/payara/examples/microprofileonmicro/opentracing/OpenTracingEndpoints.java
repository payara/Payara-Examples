package fish.payara.examples.microprofileonmicro.opentracing;

import io.opentracing.Tracer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.ClientBuilder;
import org.eclipse.microprofile.opentracing.Traced;


/**
 *
 * @author pandr
 */
@ApplicationScoped
@Path("/OpenTracing")
public class OpenTracingEndpoints {
    
    @Inject
    private Tracer tracer;
    
    @Inject
    private AreYou areYou;
    
    @Inject
    private HttpServletRequest request;
    
    @Resource
    private ManagedExecutorService executorService;
    
    @GET
    public String indirectHello() {
        return ClientBuilder.newClient().target(request.getRequestURL() + "Hello").request().get(String.class);
    }
    
    @GET
    @Path("/Hello")
    @Traced(operationName = "greet")
    public String hello() {
        tracer.activeSpan().setTag("greeting", "whyHelloThere");
        return "Good Morrow!";
    }
    
    @GET
    @Path("/Thirsty")
    public String areYouThirsty() {
        return areYou.thirsty();
    }
    
    @GET
    @Path("/Hungry")
    public String areYouHungry() {
        Future<String> future = executorService.submit(() -> areYou.hungry());
        
        String hungry = "In a food coma thanks!";
        
        try {
            hungry = future.get();
        } catch (InterruptedException | ExecutionException exception) {
            // Om nom nom
        }
        
        return hungry;
    }
}
