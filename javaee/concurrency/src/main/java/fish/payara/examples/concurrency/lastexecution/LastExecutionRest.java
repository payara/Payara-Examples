package fish.payara.examples.concurrency.lastexecution;

import jakarta.annotation.Resource;
import jakarta.enterprise.concurrent.CronTrigger;
import jakarta.enterprise.concurrent.ManagedScheduledExecutorService;
import jakarta.enterprise.concurrent.Trigger;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.time.ZoneId;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("last-execution")
public class LastExecutionRest {
    private static final Logger logger = Logger.getLogger(LastExecutionRest.class.getName());

    @Resource
    ManagedScheduledExecutorService managedScheduledExecutorService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getText() throws InterruptedException {
        logger.log(Level.INFO, String.format("Processing schedule executor: %s", managedScheduledExecutorService));
        AtomicInteger numberExecution = new AtomicInteger();
        ZoneId saoPaulo = ZoneId.of("America/Sao_Paulo");
        Trigger trigger = new CronTrigger("* * * * * *", saoPaulo);
        ScheduledFuture feature = managedScheduledExecutorService.schedule(new Callable<String>() {
            @Override
            public String call() throws Exception {
                numberExecution.getAndIncrement();
                return "numberExecution incremented";
            }
        }, trigger);
        Thread.sleep(1500);
        feature.cancel(false);
        return "SP Trigger Submitted:"+numberExecution.get();
    }
}
