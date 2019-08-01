package fish.payara.crudpersistence.healthcheck.database;

import fish.payara.crudpersistence.dao.CrudDao;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

@Health
@ApplicationScoped
public class CrudPersistenceHealthCheck implements HealthCheck {

    @Inject
    private CrudDao crudDao;

    @Override
    public HealthCheckResponse call() {
        boolean valid;
        try {
            valid = crudDao.checkDatabaseConnection();
        } catch (Throwable e) {
            valid = false;
        }

        if (valid) {
            return HealthCheckResponse.named(CrudPersistenceHealthCheck.class.getSimpleName()).up().build();
        } else {
            return HealthCheckResponse.named(CrudPersistenceHealthCheck.class.getSimpleName()).down().build();
        }
    }

}
