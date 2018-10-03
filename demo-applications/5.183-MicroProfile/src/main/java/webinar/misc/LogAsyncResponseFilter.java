package webinar.misc;

import java.io.IOException;
import javax.ws.rs.container.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import webinar.async.AsyncRootResource;

/**
 * Logs a message after methods from AsyncRootResource return
 */
@Provider
public class LogAsyncResponseFilter implements ContainerResponseFilter {

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        if (resourceInfo.getResourceClass().equals(AsyncRootResource.class)) {
            Log.info("Async request finished");
            String modifiedResponse = (String) responseContext.getEntity();
            modifiedResponse += "\n\nFinished in thread: " + Thread.currentThread().getName();
            responseContext.setEntity(modifiedResponse);
        }
    }

}
