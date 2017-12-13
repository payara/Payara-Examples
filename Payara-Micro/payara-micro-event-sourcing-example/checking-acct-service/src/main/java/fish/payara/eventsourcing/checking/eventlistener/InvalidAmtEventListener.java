package fish.payara.eventsourcing.checking.eventlistener;

import fish.payara.cloud.connectors.kafka.api.KafkaConnection;
import fish.payara.cloud.connectors.kafka.api.KafkaConnectionFactory;
import fish.payara.eventsourcing.common.event.InvalidAmt;
import fish.payara.eventsourcing.common.dto.FundTransferDTO;
import fish.payara.eventsourcing.common.dto.TransactionType;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 *
 * @author David R. Heffelfinger <dheffelfinger@ensode.com>
 */
@SessionScoped
public class InvalidAmtEventListener implements Serializable {

    @Resource(lookup = "java:module/env/KafkaConnectionFactory")
    private KafkaConnectionFactory kafkaConnectionFactory;

    public void returnFunds(@Observes InvalidAmt invalidAmt) {
        //re-deposit funds into the source account since the transaction failed.
        FundTransferDTO sourceFundTransferDTO = invalidAmt.getFundTransferDTO();
        FundTransferDTO destFundTransferDTO = buildDestFundTransferDTO(sourceFundTransferDTO);

        try (KafkaConnection kafkaConnection = kafkaConnectionFactory.createConnection()) {
            kafkaConnection.send(new ProducerRecord("savingsacct-topic", destFundTransferDTO));
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    private FundTransferDTO buildDestFundTransferDTO(FundTransferDTO sourceFundTransferDTO) {
        FundTransferDTO destFundTransferDTO = new FundTransferDTO();

        destFundTransferDTO.setAmt(sourceFundTransferDTO.getAmt());
        destFundTransferDTO.setDestAcctNbr(sourceFundTransferDTO.getSourceAcctNbr());
        destFundTransferDTO.setSourceAcctNbr(sourceFundTransferDTO.getDestAcctNbr());
        destFundTransferDTO.setTransactionType(TransactionType.DEPOSIT);

        return destFundTransferDTO;
    }
}
