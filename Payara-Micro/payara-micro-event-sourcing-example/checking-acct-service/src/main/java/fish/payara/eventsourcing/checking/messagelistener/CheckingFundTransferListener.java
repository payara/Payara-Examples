package fish.payara.eventsourcing.checking.messagelistener;

import fish.payara.cloud.connectors.kafka.api.KafkaConnection;
import fish.payara.cloud.connectors.kafka.api.KafkaConnectionFactory;
import fish.payara.cloud.connectors.kafka.api.KafkaListener;
import fish.payara.cloud.connectors.kafka.api.OnRecord;
import fish.payara.eventsourcing.checking.business.CheckingAcctMgr;
import fish.payara.eventsourcing.common.dto.AccountType;
import fish.payara.eventsourcing.common.dto.FundTransferDTO;
import fish.payara.eventsourcing.common.util.FundTransferDTOUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;

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
public class CheckingFundTransferListener implements KafkaListener {

    private static final Logger LOGGER = Logger.getLogger(CheckingFundTransferListener.class.getName());

    @Resource(lookup = "java:comp/env/KafkaConnectionFactory")
    private KafkaConnectionFactory kafkaConnectionFactory;

    @Inject
    private CheckingAcctMgr checkingAcctMgr;

    @OnRecord(topics = {"checkingacct-topic"})
    public void transferFunds(ConsumerRecord consumerRecord) {
        String fundTransferDTOJson = (String) consumerRecord.value();
        FundTransferDTO fundTransferDTO = FundTransferDTOUtil.jsonToFundTransferDTO(fundTransferDTOJson);

        if (fundTransferDTO.getSourceAcctType().equals(AccountType.CHECKING)) {
            LOGGER.log(Level.INFO, String.format("Withdrawing %.2f currency units from checking", fundTransferDTO.getAmt()));
            if (checkingAcctMgr.withdrawFunds(fundTransferDTO)) {
                try (KafkaConnection kafkaConnection = kafkaConnectionFactory.createConnection()) {
                    kafkaConnection.send(new ProducerRecord("savingsacct-topic", fundTransferDTOJson));
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                LOGGER.log(Level.WARNING, "There was a problem withdrawing funds from checking account, aborting transfer.");
            }
        } else if (fundTransferDTO.getDestAcctType().equals(AccountType.CHECKING)) {
            LOGGER.log(Level.INFO, String.format("Depositing %.2f currency units to checking", fundTransferDTO.getAmt()));
            checkingAcctMgr.depositFunds(fundTransferDTO);
        }
    }
}
