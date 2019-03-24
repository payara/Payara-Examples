package fish.payara.crudpersistence.dao;

import fish.payara.crudpersistence.Customer;
import java.sql.Connection;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class CrudDao {

    @PersistenceContext(unitName = "CustomerPersistenceUnit")
    private EntityManager em;

    public void create(Customer customer) {
        em.persist(customer);
    }

    public boolean checkDatabaseConnection() {
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
