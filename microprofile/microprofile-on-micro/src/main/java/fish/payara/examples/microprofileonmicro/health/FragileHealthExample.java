package fish.payara.examples.microprofileonmicro.health;

import java.util.Random;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

/**
 *
 * @author Andrew Pielage <andrew.pielage@payara.fish>
 */
@Health
@ApplicationScoped
public class FragileHealthExample implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("fragile");
        
        if (up()) {
            return responseBuilder.withData("CoinFlip", "Heads").up().build();
        }
        
        return responseBuilder.withData("CoinFlip", "Tails").down().build();
    }
    
    private boolean up() {
        return new Random().nextBoolean();
    }
}
