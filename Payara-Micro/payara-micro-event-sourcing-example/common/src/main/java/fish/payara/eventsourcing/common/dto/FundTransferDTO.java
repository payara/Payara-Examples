package fish.payara.eventsourcing.common.dto;

/**
 *
 * @author David R. Heffelfinger <dheffelfinger@ensode.com>
 */
public class FundTransferDTO {

    private Long sourceAcctNbr;
    private Long destAcctNbr;
    private Double amt;
    private AccountType sourceAcctType;
    private AccountType destAcctType;

    public Long getSourceAcctNbr() {
        return sourceAcctNbr;
    }

    public void setSourceAcctNbr(Long sourceAcctNbr) {
        this.sourceAcctNbr = sourceAcctNbr;
    }

    public Long getDestAcctNbr() {
        return destAcctNbr;
    }

    public void setDestAcctNbr(Long destAcctNbr) {
        this.destAcctNbr = destAcctNbr;
    }

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public AccountType getSourceAcctType() {
        return sourceAcctType;
    }

    public void setSourceAcctType(AccountType sourceAcctType) {
        this.sourceAcctType = sourceAcctType;
    }

    public AccountType getDestAcctType() {
        return destAcctType;
    }

    public void setDestAcctType(AccountType destAcctType) {
        this.destAcctType = destAcctType;
    }

}
