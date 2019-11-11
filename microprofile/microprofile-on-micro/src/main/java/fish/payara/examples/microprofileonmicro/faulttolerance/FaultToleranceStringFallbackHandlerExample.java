package fish.payara.examples.microprofileonmicro.faulttolerance;

import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;

/**
 *
 * @author Andrew Pielage <andrew.pielage@payara.fish>
 */
@ApplicationScoped
public class FaultToleranceStringFallbackHandlerExample implements FallbackHandler<String> {

    @Override
    public String handle(ExecutionContext context) {
        return "Critical Failure!";
    }
    
}
