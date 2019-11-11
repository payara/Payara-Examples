package fish.payara.examples.microprofileonmicro.faulttolerance;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.eclipse.microprofile.faulttolerance.Asynchronous;
import org.eclipse.microprofile.faulttolerance.Bulkhead;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

/**
 *
 * @author Andrew Pielage <andrew.pielage@payara.fish>
 */
@ApplicationScoped
@Path("/FaultTolerance")
public class FaultToleranceEndpoints {
    
    @GET
    @Path("/Bulkhead")
    @Bulkhead(3)
    public String bulkhead() throws InterruptedException {
        Thread.sleep(5000);
        return "Made it!";
    }
    
    @GET
    @Path("/AsyncBulkhead")
    @Asynchronous
    @Bulkhead(value = 3, waitingTaskQueue = 2)
    public Future<String> asyncBulkhead() throws InterruptedException {
        Thread.sleep(10000);
        return CompletableFuture.completedFuture("Made it in " + Thread.currentThread().getName());
    }
    
    @GET
    @Path("/CircuitBreaker")
    @CircuitBreaker(requestVolumeThreshold = 4, failureRatio = 0.75, successThreshold = 3)
    public String circuitBreaker() {
        if (new Random().nextBoolean()) {
            return "Oh Herro!";
        }
        
        throw new RuntimeException();
    }
    
    @GET
    @Path("/Fallback")
    @Fallback(fallbackMethod = "ohNoes")
    public String fallback() {
        if (new Random().nextBoolean()) {
            return "Sweeeeeet!";
        }
        
        throw new RuntimeException();
    }
    
    public String ohNoes() {
        return "Heeeeeeelllpppp!";
    }
    
    @GET
    @Path("/Retry")
    @Fallback(FaultToleranceStringFallbackHandlerExample.class)
    @Retry(maxRetries = 1, delay = 3000, abortOn = IOException.class)
    public String retry() throws IOException {
        System.out.println("Attempting!");
        
        if (new Random().nextBoolean()) {
            return "Success!";
        }
        
        if (new Random().nextBoolean()) {
            throw new IOException();
        }
        
        throw new RuntimeException();
    }
    
    @GET
    @Path("/Timeout")
    @Fallback(FaultToleranceIntegerFallbackHandlerExample.class)
    @Timeout(2500)
    public Integer timeout() throws InterruptedException {
        int sleepyTime = new Random().nextInt(5000);
        
        Thread.sleep(sleepyTime);
        
        return sleepyTime;
    }
    
    
}
