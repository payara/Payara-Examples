package fish.payara.examples.microprofileonmicro.metrics;

import java.util.Random;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.ConcurrentGauge;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Metric;
import org.eclipse.microprofile.metrics.annotation.Timed;

/**
 *
 * @author Andrew Pielage <andrew.pielage@payara.fish>
 */
@Path("/Metrics")
@ApplicationScoped
public class MetricsEndpoints {
    
    @Inject
    @Metric(name = "County", absolute = true)
    Counter county;
    
    @Inject
    private MetricRegistry metricRegistry;
    
    @GET
    @Path("/Counted")
    @Counted(name = "County", absolute = true, description = "Super basic counter example")
    public Long counted() {
        return metricRegistry.counter("County").getCount();
    }
    
    @GET
    @Path("/InjectedCounted")
    public Long injectedCounter() {
        return county.getCount();
    }
    
    @GET
    @Path("/Gauge")
    @Gauge(name = "Gaugey", unit = MetricUnits.NONE, absolute = true, description = "Super basic gauge example")
    public Integer gauge() {
        return new Random().nextInt(100);
    }
    
    @GET
    @Path("/Metered")
    @Metered(name = "Metery", absolute = true)
    public Double metered() {
        return metricRegistry.meter("Metery").getOneMinuteRate();
    }
    
    @GET
    @Path("/ConcurrentGauge")
    @ConcurrentGauge(name = "Caugey", absolute = true)
    public Long concurrentGauge() throws InterruptedException {
        Thread.sleep(5000);
        return metricRegistry.concurrentGauge("Caugey").getCount();
    }
    
    @GET
    @Path("Timed")
    @Timed(name = "Timey", absolute = true, unit = "milliseconds")
    public String timed() throws InterruptedException {
        Thread.sleep(new Random().nextInt(5000));
        return "Yawn...";
    }
}
