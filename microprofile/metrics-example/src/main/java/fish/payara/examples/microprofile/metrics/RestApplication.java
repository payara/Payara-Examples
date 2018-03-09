package fish.payara.examples.microprofile.metrics;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Created by mike on 12/01/18.
 */
@ApplicationPath("/")
@ApplicationScoped
public class RestApplication extends Application {
}
