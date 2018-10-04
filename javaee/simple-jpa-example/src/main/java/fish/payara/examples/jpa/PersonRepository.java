package fish.payara.examples.jpa;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import static javax.transaction.Transactional.TxType.REQUIRED;

@ApplicationScoped
public class PersonRepository {

    @PersistenceContext(unitName = "SAMPLE_PU")
    private EntityManager em;

    @Transactional(REQUIRED)
    public void create(Person person) {
        em.persist(person);
    }

    @Transactional(REQUIRED)
    public Person edit(Person person) {
        return em.merge(person);
    }

    @Transactional(REQUIRED)
    public void remove(Person person) {
        em.remove(em.merge(person));
    }

    public Person find(Long id) {
        return em.find(Person.class, id);
    }

    public List<Person> findAll() {
        return em.createQuery("SELECT p FROM Person p", Person.class).getResultList();
    }
}
