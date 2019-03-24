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

    @Inject
    private CrudDao crudDao;

    @Override
    public HealthCheckResponse call() {
        LOG.log(Level.INFO, "--------- call() invoked");
        boolean valid;
        try {
            valid = crudDao.checkDatabaseConnection();
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

}
