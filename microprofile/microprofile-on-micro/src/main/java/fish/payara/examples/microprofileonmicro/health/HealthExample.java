package fish.payara.examples.microprofileonmicro.health;

import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

/**
 *
 * @author Andrew Pielage <andrew.pielage@payara.fish>
 */
@Health
@ApplicationScoped
public class HealthExample implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("always-alive").withData("I will", "survive").up().build();
    }
    
    
}
