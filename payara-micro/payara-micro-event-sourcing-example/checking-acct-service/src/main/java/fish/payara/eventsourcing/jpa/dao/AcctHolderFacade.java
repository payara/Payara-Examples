package fish.payara.eventsourcing.jpa.dao;

import fish.payara.eventsourcing.jpa.entities.AcctHolder;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author David R. Heffelfinger <dheffelfinger@ensode.com>
 */
@Stateless
public class AcctHolderFacade extends AbstractFacade<AcctHolder> {

    @PersistenceContext(unitName = "fish.payara.eventsourcing_savings-acct-service_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AcctHolderFacade() {
        super(AcctHolder.class);
    }
    
}
