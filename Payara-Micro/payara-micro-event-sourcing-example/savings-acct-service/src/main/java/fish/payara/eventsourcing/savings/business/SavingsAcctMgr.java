package fish.payara.eventsourcing.savings.business;

import fish.payara.eventsourcing.common.event.InvalidAmt;
import fish.payara.eventsourcing.common.dto.FundTransferDTO;
import fish.payara.eventsourcing.jpa.dao.SavingsAcctFacade;
import fish.payara.eventsourcing.jpa.entities.SavingsAcct;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author David R. Heffelfinger <dheffelfinger@ensode.com>
 */
@RequestScoped
@Transactional
public class SavingsAcctMgr implements Serializable {

    @Inject
    private SavingsAcctFacade savingsAcctFacade;

    @Inject
    private Event<InvalidAmt> invalidAmtEvent;

    public void withdrawFunds(FundTransferDTO fundTransferDTO) {
        SavingsAcct savingsAcct = savingsAcctFacade.findByAcctNbr(fundTransferDTO.getDestAcctNbr());

        if (fundTransferDTO.getAmt() > savingsAcct.getAcctBalance()) {
            InvalidAmt invalidAmt = new InvalidAmt(fundTransferDTO);
            invalidAmtEvent.fire(invalidAmt);
        } else {
            savingsAcct.setAcctBalance(savingsAcct.getAcctBalance() - fundTransferDTO.getAmt());
            savingsAcctFacade.edit(savingsAcct);
        }
    }

    public void depositFunds(FundTransferDTO fundTransferDTO) {
        //TODO: Invoke with an invalid account number to illustrate "rolling back" a transaction
        SavingsAcct savingsAcct = savingsAcctFacade.findByAcctNbr(fundTransferDTO.getDestAcctNbr());

        savingsAcct.setAcctBalance(savingsAcct.getAcctBalance() + fundTransferDTO.getAmt());
        savingsAcctFacade.edit(savingsAcct);

    }
}
