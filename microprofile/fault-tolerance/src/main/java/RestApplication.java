import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @author Mike Croft
 */

@ApplicationPath("/")
@ApplicationScoped
public class RestApplication extends Application {
}