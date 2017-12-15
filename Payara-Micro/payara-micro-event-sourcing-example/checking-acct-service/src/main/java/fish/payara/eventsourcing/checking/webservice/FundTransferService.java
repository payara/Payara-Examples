package fish.payara.eventsourcing.checking.webservice;

import fish.payara.cloud.connectors.kafka.api.KafkaConnection;
import fish.payara.cloud.connectors.kafka.api.KafkaConnectionFactory;
import fish.payara.eventsourcing.checking.business.CheckingAcctMgr;
import fish.payara.eventsourcing.common.dto.AccountType;
import fish.payara.eventsourcing.common.dto.FundTransferDTO;
import fish.payara.eventsourcing.common.util.FundTransferDTOUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * Transfers funds between accounts.
 *
 * @author David R. Heffelfinger <dheffelfinger@ensode.com>
 */
@Path("transfer-funds")
public class FundTransferService {

    @Inject
    private CheckingAcctMgr checkingAcctMgr;

    @Resource(lookup = "java:comp/env/KafkaConnectionFactory")
    private KafkaConnectionFactory kafkaConnectionFactory;

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void transferFunds(String fundTransferJsonData)  {
        FundTransferDTO fundTransferDTO = FundTransferDTOUtil.jsonToFundTransferDTO(fundTransferJsonData);

        if (fundTransferDTO.getSourceAcctType().equals(AccountType.CHECKING)) {
            checkingAcctMgr.withdrawFunds(fundTransferDTO);

            try (KafkaConnection kafkaConnection = kafkaConnectionFactory.createConnection()) {
                kafkaConnection.send(new ProducerRecord("savingsacct-topic", fundTransferJsonData));
            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            }
        } 
        //for brevity, we are not implementing transfering from checking to savings
    }

   
}
