package fish.payara.eventsourcing.common.event;

import fish.payara.eventsourcing.common.dto.FundTransferDTO;

/**
 *
 * @author David R. Heffelfinger <dheffelfinger@ensode.com>
 */
public class InvalidAmt {

    public InvalidAmt() {
    }

    public InvalidAmt(FundTransferDTO fundTransferDTO) {
        this.fundTransferDTO = fundTransferDTO;
    }

    private FundTransferDTO fundTransferDTO;

    public FundTransferDTO getFundTransferDTO() {
        return fundTransferDTO;
    }

    public void setFundTransferDTO(FundTransferDTO fundTransferDTO) {
        this.fundTransferDTO = fundTransferDTO;
    }

}
