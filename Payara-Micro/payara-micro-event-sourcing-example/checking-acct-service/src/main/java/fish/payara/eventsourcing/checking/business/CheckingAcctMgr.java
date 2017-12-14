package fish.payara.eventsourcing.checking.business;

import fish.payara.eventsourcing.common.event.InvalidAmt;
import fish.payara.eventsourcing.common.dto.FundTransferDTO;
import fish.payara.eventsourcing.jpa.dao.CheckingAcctFacade;
import fish.payara.eventsourcing.jpa.entities.CheckingAcct;
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
public class CheckingAcctMgr implements Serializable {

    @Inject
    private CheckingAcctFacade checkingAcctFacade;
    
    @Inject
    private Event<InvalidAmt> invalidAmtEvent;

    public void withdrawFunds(FundTransferDTO fundTransferDTO)  {

        CheckingAcct checkingAcct = checkingAcctFacade.findByAcctNbr(fundTransferDTO.getSourceAcctNbr());

        if (fundTransferDTO.getAmt() > checkingAcct.getAcctBalance()) {
            InvalidAmt invalidAmt = new InvalidAmt(fundTransferDTO);
            invalidAmtEvent.fire(invalidAmt);
        } else {
            checkingAcct.setAcctBalance(checkingAcct.getAcctBalance() - fundTransferDTO.getAmt());
            checkingAcctFacade.edit(checkingAcct);
        }
    }

    public void depositFunds(FundTransferDTO fundTransferDTO)  {
        CheckingAcct checkingAcct = checkingAcctFacade.findByAcctNbr(fundTransferDTO.getDestAcctNbr());

        checkingAcct.setAcctBalance(checkingAcct.getAcctBalance() + fundTransferDTO.getAmt());
        checkingAcctFacade.edit(checkingAcct);
    }
}
