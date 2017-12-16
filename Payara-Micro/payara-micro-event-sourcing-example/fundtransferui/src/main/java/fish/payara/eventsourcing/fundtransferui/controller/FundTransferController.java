package fish.payara.eventsourcing.fundtransferui.controller;

import fish.payara.cloud.connectors.kafka.api.KafkaConnection;
import fish.payara.cloud.connectors.kafka.api.KafkaConnectionFactory;
import fish.payara.eventsourcing.common.dto.AccountType;
import fish.payara.eventsourcing.common.dto.FundTransferDTO;
import fish.payara.eventsourcing.common.util.FundTransferDTOUtil;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 *
 * @author David R. Heffelfinger <dheffelfinger@ensode.com>
 */
@Named
@ViewScoped
public class FundTransferController implements Serializable {

    @Resource(lookup = "java:module/env/KafkaConnectionFactory")
    private KafkaConnectionFactory kafkaConnectionFactory;

    @Inject
    private FacesContext facesContext;

    private static final Logger LOGGER = Logger.getLogger(FundTransferController.class.getName());

    public void transferFunds() {
        LOGGER.log(Level.INFO, String.format("{0}.transferFunds() invoked", this.getClass().getClass()));
        FundTransferDTO fundTransferDTO = new FundTransferDTO();

        fundTransferDTO.setSourceAcctType(AccountType.CHECKING);
        fundTransferDTO.setSourceAcctNbr(1234L);
        fundTransferDTO.setDestAcctType(AccountType.SAVINGS);
        fundTransferDTO.setDestAcctNbr(1123L);
        fundTransferDTO.setAmt(100.00);
        sendCheckingMessage(fundTransferDTO);
        facesContext.addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Transfer funds message sent successfully",
                "Transfer funds message sent successfully"));
    }

    public void simulateTransactionError() {
        LOGGER.log(Level.INFO, String.format("{0}.simulateTransactionError() invoked", this.getClass().getClass()));
        FundTransferDTO fundTransferDTO = new FundTransferDTO();

        fundTransferDTO.setSourceAcctType(AccountType.CHECKING);
        fundTransferDTO.setSourceAcctNbr(1234L); //checking account number
        fundTransferDTO.setDestAcctType(AccountType.SAVINGS);
        fundTransferDTO.setDestAcctNbr(2222L); //invalid savings account number, should trigger sending a compensating transaction to "rollback" the checking transaction
        fundTransferDTO.setAmt(100.00); //amount to transfer
        sendCheckingMessage(fundTransferDTO);
        facesContext.addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Message simulating transaction error sent successfully.",
                "Message simulating transaction error sent successfully."));
    }

    private void sendCheckingMessage(FundTransferDTO fundTransferDTO) {
        String fundTransferDTOJson;

        fundTransferDTOJson = FundTransferDTOUtil.fundTransferDTOToJson(fundTransferDTO);

        try (KafkaConnection kafkaConnection = kafkaConnectionFactory.createConnection()) {
            kafkaConnection.send(new ProducerRecord("checkingacct-topic",
                    fundTransferDTOJson));
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
}
