package fish.payara.examples.microprofileonmicro.faulttolerance;

import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;

/**
 *
 * @author Andrew Pielage <andrew.pielage@payara.fish>
 */
@ApplicationScoped
public class FaultToleranceIntegerFallbackHandlerExample implements FallbackHandler<Integer> {

    @Override
    public Integer handle(ExecutionContext context) {
        return Integer.MAX_VALUE;
    }
    
}
