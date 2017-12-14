package fish.payara.eventsourcing.jpa.dao;

import fish.payara.eventsourcing.jpa.entities.SavingsAcct;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author David R. Heffelfinger <dheffelfinger@ensode.com>
 */
@Stateless
public class SavingsAcctFacade extends AbstractFacade<SavingsAcct> {

    private static final Logger LOGGER = Logger.getLogger(SavingsAcctFacade.class.getName());

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
        SavingsAcct savingsAcct = null;
        TypedQuery<SavingsAcct> typedQuery = em.createNamedQuery("SavingsAcct.findByAcctNbr", SavingsAcct.class);
        typedQuery.setParameter("acctNbr", acctNbr);

        try {
            savingsAcct = typedQuery.getSingleResult();
        } catch (NoResultException noResultException) {
               LOGGER.log(Level.SEVERE, String.format("Savings account %d not found", acctNbr));
        }

        return savingsAcct;
    }

}
