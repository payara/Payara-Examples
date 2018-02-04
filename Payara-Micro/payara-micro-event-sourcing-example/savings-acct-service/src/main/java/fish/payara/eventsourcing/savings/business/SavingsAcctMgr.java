package fish.payara.eventsourcing.savings.business;

import fish.payara.cloud.connectors.kafka.api.KafkaConnection;
import fish.payara.cloud.connectors.kafka.api.KafkaConnectionFactory;
import fish.payara.eventsourcing.common.dto.AccountType;
import fish.payara.eventsourcing.common.dto.FundTransferDTO;
import fish.payara.eventsourcing.common.util.FundTransferDTOUtil;
import fish.payara.eventsourcing.jpa.dao.SavingsAcctFacade;
import fish.payara.eventsourcing.jpa.entities.SavingsAcct;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 *
 * @author David R. Heffelfinger <dheffelfinger@ensode.com>
 */
@RequestScoped
@Transactional
public class SavingsAcctMgr implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(SavingsAcctMgr.class.getName());

    @Resource(lookup = "java:module/env/KafkaConnectionFactory")
    private KafkaConnectionFactory kafkaConnectionFactory;

    @Inject
    private SavingsAcctFacade savingsAcctFacade;

    public void withdrawFunds(FundTransferDTO fundTransferDTO) {
        SavingsAcct savingsAcct = savingsAcctFacade.findByAcctNbr(fundTransferDTO.getDestAcctNbr());

        savingsAcct.setAcctBalance(savingsAcct.getAcctBalance() - fundTransferDTO.getAmt());
        savingsAcctFacade.edit(savingsAcct);
    }

    public void depositFunds(FundTransferDTO fundTransferDTO) {
        SavingsAcct savingsAcct = savingsAcctFacade.findByAcctNbr(fundTransferDTO.getDestAcctNbr());

        if (savingsAcct == null) {
            //invalid savings account, can't deposit, need to send a compensating transaction to undo the withdrawal in the savings account.
            LOGGER.log(Level.WARNING, String.format("Received invalid savings account number: %d", fundTransferDTO.getDestAcctNbr()));

            //switch the source and destination accounts
            fundTransferDTO.setDestAcctType(AccountType.CHECKING);
            fundTransferDTO.setDestAcctNbr(fundTransferDTO.getSourceAcctNbr());
            fundTransferDTO.setSourceAcctType(AccountType.SAVINGS);
            fundTransferDTO.setSourceAcctNbr(fundTransferDTO.getDestAcctNbr());

            //generate a compensating transaction to re-deposit the funds into the checking account
            try (KafkaConnection kafkaConnection = kafkaConnectionFactory.createConnection()) {
                kafkaConnection.send(new ProducerRecord("checkingacct-topic", FundTransferDTOUtil.fundTransferDTOToJson(fundTransferDTO)));
            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            savingsAcct.setAcctBalance(savingsAcct.getAcctBalance() + fundTransferDTO.getAmt());
            savingsAcctFacade.edit(savingsAcct);
        }

    }
}
