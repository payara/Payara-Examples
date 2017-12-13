package fish.payara.eventsourcing.checking.webservice;

import fish.payara.cloud.connectors.kafka.api.KafkaConnection;
import fish.payara.cloud.connectors.kafka.api.KafkaConnectionFactory;
import fish.payara.eventsourcing.checking.business.CheckingAcctMgr;
import fish.payara.eventsourcing.common.dto.FundTransferDTO;
import fish.payara.eventsourcing.common.dto.TransactionType;
import fish.payara.eventsourcing.common.exception.InvalidTransactionTypeException;
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
 * Transfers funds from checkings to savings.
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
    public void transferFunds(String fundTransferJsonData) throws InvalidTransactionTypeException {
        FundTransferDTO fundTransferDTO = FundTransferDTOUtil.jsonToFundTransferDTO(fundTransferJsonData);
        String destFundTransferDTOJson;

        if (fundTransferDTO.getTransactionType().equals(TransactionType.WITHDRAWAL)) {
            checkingAcctMgr.withdrawFunds(fundTransferDTO);
            FundTransferDTO destFundTransferDTO = buildDestFundTransferDTO(fundTransferDTO);

            try (KafkaConnection kafkaConnection = kafkaConnectionFactory.createConnection()) {
                destFundTransferDTOJson = FundTransferDTOUtil.fundTransferDTOToJson(destFundTransferDTO);
                kafkaConnection.send(new ProducerRecord("savingsacct-topic", destFundTransferDTOJson));
            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            throw new InvalidTransactionTypeException(
                    String.format("Invalid transaction type, expected \"withdrawal\", received %s",
                            fundTransferDTO.getTransactionType().toString()));
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
