package fish.payara.crudpersistence.healthcheck.database;

import fish.payara.crudpersistence.dao.CrudDao;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

@Health
@ApplicationScoped
public class CrudPersistenceHealthCheck implements HealthCheck {

    private static final Logger LOG = Logger.getLogger(CrudPersistenceHealthCheck.class.getName());

    @PersistenceContext(unitName = "CustomerPersistenceUnit")
    private EntityManager em;

    @Override
    public HealthCheckResponse call() {
        LOG.log(Level.INFO, "--------- call() invoked");
        boolean valid;
        try {
            valid = checkDatabaseConnection();
            LOG.log(Level.INFO, "--------- valid is: {0}", valid);
        } catch (Throwable e) {
            // Proxy can already thrown an error
            LOG.log(Level.SEVERE, "Exception caught", e);
            valid = false;
        }

        if (valid) {
            return HealthCheckResponse.named(CrudPersistenceHealthCheck.class.getSimpleName()).up().build();
        } else {
            return HealthCheckResponse.named(CrudPersistenceHealthCheck.class.getSimpleName()).down().build();
        }
    }

    private boolean checkDatabaseConnection() {
        boolean result;
        try {
            Connection connection = em.unwrap(Connection.class);
            result = connection.isValid(0);
        } catch (Throwable e) {
            result = false;
        }
        return result;

    }

}
