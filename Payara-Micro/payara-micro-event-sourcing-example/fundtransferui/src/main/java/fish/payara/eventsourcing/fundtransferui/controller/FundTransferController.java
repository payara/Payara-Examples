package fish.payara.eventsourcing.fundtransferui.controller;

import fish.payara.eventsourcing.common.dto.FundTransferDTO;
import fish.payara.eventsourcing.common.dto.TransactionType;
import fish.payara.eventsourcing.common.util.FundTransferDTOUtil;
import fish.payara.eventsourcing.fundtransferui.webserviceclient.CheckingAcctServiceClient;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author David R. Heffelfinger <dheffelfinger@ensode.com>
 */
@Named
@ViewScoped
public class FundTransferController implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(FundTransferController.class.getName());

    public void transferFunds(ActionEvent actionEvent) {
        LOGGER.log(Level.INFO, String.format("{0}.transferFunds() invoked", this.getClass().getClass()));
        FundTransferDTO fundTransferDTO = new FundTransferDTO();
        CheckingAcctServiceClient checkingAcctServiceClient = new CheckingAcctServiceClient();
        String fundTransferDTOJson;

        fundTransferDTO.setSourceAcctNbr(1123L);
        fundTransferDTO.setDestAcctNbr(1234L);
        fundTransferDTO.setTransactionType(TransactionType.WITHDRAWAL);
        fundTransferDTO.setAmt(100.00);

        fundTransferDTOJson = FundTransferDTOUtil.fundTransferDTOToJson(fundTransferDTO);

        checkingAcctServiceClient.transferFunds(fundTransferDTOJson);

        checkingAcctServiceClient.close();
    }

}
