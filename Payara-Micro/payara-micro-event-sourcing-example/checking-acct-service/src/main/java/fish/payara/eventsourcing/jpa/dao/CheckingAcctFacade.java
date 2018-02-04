package fish.payara.eventsourcing.jpa.dao;

import fish.payara.eventsourcing.jpa.entities.CheckingAcct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author David R. Heffelfinger <dheffelfinger@ensode.com>
 */
@Stateless
public class CheckingAcctFacade extends AbstractFacade<CheckingAcct> {

    @PersistenceContext(unitName = "fish.payara.eventsourcing_savings-acct-service_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CheckingAcctFacade() {
        super(CheckingAcct.class);
    }

    public CheckingAcct findByAcctNbr(Long acctNbr) {
        TypedQuery<CheckingAcct> typedQuery = em.createNamedQuery("CheckingAcct.findByAcctNbr", CheckingAcct.class);
        typedQuery.setParameter("acctNbr", acctNbr);

        return typedQuery.getSingleResult();
    }

}
