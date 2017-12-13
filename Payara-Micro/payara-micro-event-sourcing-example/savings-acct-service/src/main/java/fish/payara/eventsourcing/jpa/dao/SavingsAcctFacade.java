package fish.payara.eventsourcing.jpa.dao;

import fish.payara.eventsourcing.jpa.entities.SavingsAcct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author David R. Heffelfinger <dheffelfinger@ensode.com>
 */
@Stateless
public class SavingsAcctFacade extends AbstractFacade<SavingsAcct> {

    @PersistenceContext(unitName = "fish.payara.eventsourcing_savings-acct-service_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SavingsAcctFacade() {
        super(SavingsAcct.class);
    }

    public SavingsAcct findByAcctNbr(Long acctNbr) {
        TypedQuery<SavingsAcct> typedQuery = em.createNamedQuery("SavingsAcct.findByAcctNbr", SavingsAcct.class);
        typedQuery.setParameter("acctNbr", acctNbr);

        return typedQuery.getSingleResult();
    }
    
}
