package fish.payara.eventsourcing.checking.business;

import fish.payara.eventsourcing.common.dto.FundTransferDTO;
import fish.payara.eventsourcing.jpa.dao.CheckingAcctFacade;
import fish.payara.eventsourcing.jpa.entities.CheckingAcct;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
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

    public void withdrawFunds(FundTransferDTO fundTransferDTO) {
        CheckingAcct checkingAcct = checkingAcctFacade.findByAcctNbr(fundTransferDTO.getSourceAcctNbr());
        checkingAcct.setAcctBalance(checkingAcct.getAcctBalance() - fundTransferDTO.getAmt());
        checkingAcctFacade.edit(checkingAcct);
    }

    public void depositFunds(FundTransferDTO fundTransferDTO) {
        CheckingAcct checkingAcct = checkingAcctFacade.findByAcctNbr(fundTransferDTO.getDestAcctNbr());

        checkingAcct.setAcctBalance(checkingAcct.getAcctBalance() + fundTransferDTO.getAmt());
        checkingAcctFacade.edit(checkingAcct);
    }
}
