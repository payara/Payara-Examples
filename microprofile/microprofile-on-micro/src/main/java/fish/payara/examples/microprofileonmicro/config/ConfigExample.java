package fish.payara.examples.microprofileonmicro.config;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author Andrew Pielage <andrew.pielage@payara.fish>
 */
@RequestScoped
public class ConfigExample {
    
    @Inject
    @ConfigProperty(name = "fish.payara.examples.config.property", defaultValue = "Wibbles")
    private String exampleProperty;
    
    @Inject
    @ConfigProperty(name = "fish.payara.examples.another.config.property", defaultValue = "0")
    private int anotherExampleProperty;
    
    public String getExampleProperty() {
        return exampleProperty;
    }
    
    public int getAnotherExampleProperty() {
        return anotherExampleProperty;
    }
}
