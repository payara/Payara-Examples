package fish.payara.eventsourcing.checking.messagelistener;

import fish.payara.cloud.connectors.kafka.api.KafkaListener;
import fish.payara.cloud.connectors.kafka.api.OnRecord;
import fish.payara.eventsourcing.common.dto.AccountType;
import fish.payara.eventsourcing.common.dto.FundTransferDTO;
import fish.payara.eventsourcing.common.util.FundTransferDTOUtil;
import fish.payara.eventsourcing.savings.business.SavingsAcctMgr;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 *
 * @author David R. Heffelfinger <dheffelfinger@ensode.com>
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "savingsAcctFundTransferListener")
    ,
    @ActivationConfigProperty(propertyName = "groupIdConfig", propertyValue = "fundTransfer")
    ,
    @ActivationConfigProperty(propertyName = "topics", propertyValue = "savingsacct-topic")
    ,
    @ActivationConfigProperty(propertyName = "bootstrapServersConfig", propertyValue = "localhost:9092")
    ,   
    @ActivationConfigProperty(propertyName = "autoCommitInterval", propertyValue = "100")
    ,   
    @ActivationConfigProperty(propertyName = "retryBackoff", propertyValue = "1000")
    ,   
    @ActivationConfigProperty(propertyName = "keyDeserializer", propertyValue = "org.apache.kafka.common.serialization.StringDeserializer")
    ,   
    @ActivationConfigProperty(propertyName = "valueDeserializer", propertyValue = "org.apache.kafka.common.serialization.StringDeserializer")
    ,   
    @ActivationConfigProperty(propertyName = "pollInterval", propertyValue = "1000"),})
public class SavingsFundTransferListener implements KafkaListener {

    private static final Logger LOGGER = Logger.getLogger(SavingsFundTransferListener.class.getName());

    @Inject
    private SavingsAcctMgr savingsAcctMgr;

    @OnRecord(topics = {"savingsacct-topic"})
    public void transferFunds(ConsumerRecord consumerRecord) {
        String fundTransferDTOJson = (String) consumerRecord.value();
        FundTransferDTO fundTransferDTO = FundTransferDTOUtil.jsonToFundTransferDTO(fundTransferDTOJson);

        if (fundTransferDTO.getDestAcctType().equals(AccountType.SAVINGS)) {
            LOGGER.log(Level.INFO, String.format("Depositing %.2f currency units to savings", fundTransferDTO.getAmt()));
            savingsAcctMgr.depositFunds(fundTransferDTO);
        } else if (fundTransferDTO.getSourceAcctType().equals(AccountType.SAVINGS)) {
            LOGGER.log(Level.INFO, String.format("Withdrawing %.2f currency units from savings", fundTransferDTO.getAmt()));
            savingsAcctMgr.withdrawFunds(fundTransferDTO);
        }
    }
}
