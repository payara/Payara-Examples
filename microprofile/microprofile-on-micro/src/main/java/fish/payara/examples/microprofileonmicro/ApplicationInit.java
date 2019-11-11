package fish.payara.examples.microprofileonmicro;

import javax.annotation.security.DeclareRoles;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.eclipse.microprofile.auth.LoginConfig;

/**
 *
 * @author Andrew Pielage <andrew.pielage@payara.fish>
 */
@ApplicationPath("/")
@ApplicationScoped
@LoginConfig(authMethod = "MP-JWT")
@DeclareRoles({"payara", "goldfish", "swordfish"})
public class ApplicationInit extends Application {
    
}
