package fish.payara.eventsourcing.checking.messagelistener;

import fish.payara.cloud.connectors.kafka.api.KafkaListener;
import fish.payara.cloud.connectors.kafka.api.OnRecord;
import fish.payara.eventsourcing.checking.business.CheckingAcctMgr;
import fish.payara.eventsourcing.common.dto.FundTransferDTO;
import fish.payara.eventsourcing.common.dto.TransactionType;
import fish.payara.eventsourcing.common.util.FundTransferDTOUtil;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 *
 * @author David R. Heffelfinger <dheffelfinger@ensode.com>
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "checkingAcctFundTransferListener")
    ,
    @ActivationConfigProperty(propertyName = "groupIdConfig", propertyValue = "fundTransfer")
    ,
    @ActivationConfigProperty(propertyName = "topics", propertyValue = "checkingacct-topic")
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
public class FundTransferListener implements KafkaListener {
    
    @Inject
    private CheckingAcctMgr checkingAcctMgr;

    @OnRecord(topics = {"checkingacct-topic"})
    public void transferFunds(ConsumerRecord consumerRecord) {
        String fundTransferDTOJson = (String) consumerRecord.value();
        FundTransferDTO fundTransferDTO = FundTransferDTOUtil.jsonToFundTransferDTO(fundTransferDTOJson);

        if (fundTransferDTO.getTransactionType().equals(TransactionType.DEPOSIT)) {
            checkingAcctMgr.depositFunds(fundTransferDTO);
        } else if (fundTransferDTO.getTransactionType().equals(TransactionType.WITHDRAWAL)) {
            checkingAcctMgr.withdrawFunds(fundTransferDTO);
        }
    }
}
