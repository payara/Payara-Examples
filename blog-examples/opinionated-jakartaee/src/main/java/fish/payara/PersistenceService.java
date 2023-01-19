package fish.payara;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class PersistenceService {


	@PersistenceContext
	private EntityManager entityManager;

	public HelloEntity save(final HelloEntity helloEntity) {
		entityManager.persist(helloEntity);

		return helloEntity;
	}

	public HelloEntity find(final Long id) {
		return entityManager.find(HelloEntity.class, id);
	}
}
