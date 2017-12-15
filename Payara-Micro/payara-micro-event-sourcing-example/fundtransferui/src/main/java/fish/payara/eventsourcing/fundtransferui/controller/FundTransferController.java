package fish.payara.eventsourcing.fundtransferui.controller;

import fish.payara.eventsourcing.common.dto.AccountType;
import fish.payara.eventsourcing.common.dto.FundTransferDTO;
import fish.payara.eventsourcing.common.util.FundTransferDTOUtil;
import fish.payara.eventsourcing.fundtransferui.webserviceclient.CheckingAcctServiceClient;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author David R. Heffelfinger <dheffelfinger@ensode.com>
 */
@Named
@ViewScoped
public class FundTransferController implements Serializable {
    
    @Inject
    private FacesContext facesContext;
    
    private static final Logger LOGGER = Logger.getLogger(FundTransferController.class.getName());
    
    public void transferFunds() {
        LOGGER.log(Level.INFO, String.format("{0}.transferFunds() invoked", this.getClass().getClass()));
        FundTransferDTO fundTransferDTO = new FundTransferDTO();
        CheckingAcctServiceClient checkingAcctServiceClient;
        String fundTransferDTOJson;
        
        fundTransferDTO.setSourceAcctType(AccountType.CHECKING);
        fundTransferDTO.setSourceAcctNbr(1234L);
        fundTransferDTO.setDestAcctType(AccountType.SAVINGS);
        fundTransferDTO.setDestAcctNbr(1123L);
        fundTransferDTO.setAmt(100.00);
        try {
            checkingAcctServiceClient = new CheckingAcctServiceClient();
            fundTransferDTOJson = FundTransferDTOUtil.fundTransferDTOToJson(fundTransferDTO);
            checkingAcctServiceClient.transferFunds(fundTransferDTOJson);
            checkingAcctServiceClient.close();
            facesContext.addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Transfer successful",
                    "Transfer successful"));
        } catch (Exception e) {
            facesContext.addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "There was an error transferring funds",
                    "There was an error transferring funds"));
            e.printStackTrace();
        }
    }

    public void simulateTransactionError() {
        LOGGER.log(Level.INFO, String.format("{0}.simulateTransactionError() invoked", this.getClass().getClass()));
        FundTransferDTO fundTransferDTO = new FundTransferDTO();
        CheckingAcctServiceClient checkingAcctServiceClient;
        String fundTransferDTOJson;
        
        fundTransferDTO.setSourceAcctType(AccountType.CHECKING);
        fundTransferDTO.setSourceAcctNbr(1234L); //checking account number
        fundTransferDTO.setDestAcctType(AccountType.SAVINGS);
        fundTransferDTO.setDestAcctNbr(2222L); //invalid savings account number, should trigger sending a compensating transaction to "rollback" the checking transaction
        fundTransferDTO.setAmt(100.00); //amount to transfer
        try {
            checkingAcctServiceClient = new CheckingAcctServiceClient();
            fundTransferDTOJson = FundTransferDTOUtil.fundTransferDTOToJson(fundTransferDTO);
            checkingAcctServiceClient.transferFunds(fundTransferDTOJson);
            checkingAcctServiceClient.close();
            facesContext.addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Checking transaction rolled back",
                    "Checking transaction rolled back"));
        } catch (Exception e) {
            facesContext.addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "There was an error rolling back the transaction",
                    "There was an error rolling back the transaction"));
            e.printStackTrace();
        }
    }
}
