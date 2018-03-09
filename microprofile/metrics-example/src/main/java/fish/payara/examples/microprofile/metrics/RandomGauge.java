package fish.payara.examples.microprofile.metrics;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Gauge;

/**
 *
 * @author Ondrej Mihalyi
 */
@ApplicationScoped
public class RandomGauge {
    
    private void init(@Observes @Initialized(ApplicationScoped.class) Object startEvent) {
        // to trigger instance creation and hence activate the gauge
    }
    
    @Gauge(unit = MetricUnits.NONE)
    public double getRandomGauge() {
        return Math.random();
    }
}

