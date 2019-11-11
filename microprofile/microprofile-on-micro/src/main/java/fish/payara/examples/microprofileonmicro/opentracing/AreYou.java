package fish.payara.examples.microprofileonmicro.opentracing;

import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.opentracing.Traced;

/**
 *
 * @author Andrew Pielage <andrew.pielage@payara.fish>
 */
@ApplicationScoped
@Traced
public class AreYou {
    
    public String thirsty() {
        return "Tea Guv'na?";
    }

    public String hungry() {
        return "How 'bout a biscuit?";
    }
}
