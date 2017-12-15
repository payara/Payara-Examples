package fish.payara.eventsourcing.checking.eventlistener;

import fish.payara.cloud.connectors.kafka.api.KafkaConnection;
import fish.payara.cloud.connectors.kafka.api.KafkaConnectionFactory;
import fish.payara.eventsourcing.common.dto.AccountType;
import fish.payara.eventsourcing.common.event.InvalidAmt;
import fish.payara.eventsourcing.common.dto.FundTransferDTO;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.event.Observes;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 *
 * @author David R. Heffelfinger <dheffelfinger@ensode.com>
 */
public class InvalidAmtEventListener {
    
    @Resource(lookup = "java:module/env/KafkaConnectionFactory")
    private KafkaConnectionFactory kafkaConnectionFactory;
    
    public void returnFunds(@Observes InvalidAmt invalidAmt) {
        //re-deposit funds into the source account since the transaction failed.
        FundTransferDTO sourceFundTransferDTO = invalidAmt.getFundTransferDTO();
        FundTransferDTO destFundTransferDTO = buildDestFundTransferDTO(sourceFundTransferDTO);
        
        try (KafkaConnection kafkaConnection = kafkaConnectionFactory.createConnection()) {
            kafkaConnection.send(new ProducerRecord("checkingacct-topic", destFundTransferDTO));
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private FundTransferDTO buildDestFundTransferDTO(FundTransferDTO sourceFundTransferDTO) {
        FundTransferDTO destFundTransferDTO = new FundTransferDTO();
        
        destFundTransferDTO.setAmt(sourceFundTransferDTO.getAmt());
        destFundTransferDTO.setSourceAcctType(AccountType.SAVINGS);
        destFundTransferDTO.setDestAcctType(AccountType.CHECKING);
        destFundTransferDTO.setDestAcctNbr(sourceFundTransferDTO.getSourceAcctNbr());
        destFundTransferDTO.setSourceAcctNbr(sourceFundTransferDTO.getDestAcctNbr());
        
        return destFundTransferDTO;
    }
}
