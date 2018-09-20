package webinar.async;

import java.util.concurrent.CompletionStage;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import webinar.misc.Log;

/**
 * REST Web Service
 *
 * @author omihalyi
 */
@Path("async")
@Produces(MediaType.TEXT_PLAIN)
@RequestScoped
public class AsyncRootResource {

    @Inject
    @RestClient
    AsyncHelloClient helloClient;
    
    @GET
    public CompletionStage<String> hello() {
        Log.info("Async request started");
        String startThreadName = Thread.currentThread().getName();
        return helloClient.getGreeting("Hello")
                .thenApply(greeting -> {
                    return greeting + "\n\nStarted in thread: " + startThreadName;
                });
        // request is finished in a separate thread, this is logged in LogResponseFilter
    }
    
}
