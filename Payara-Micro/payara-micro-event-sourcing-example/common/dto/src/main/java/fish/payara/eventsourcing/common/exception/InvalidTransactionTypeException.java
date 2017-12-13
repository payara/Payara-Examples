package fish.payara.eventsourcing.common.exception;

/**
 *
 * @author David R. Heffelfinger <dheffelfinger@ensode.com>
 */
public class InvalidTransactionTypeException extends Exception {

    public InvalidTransactionTypeException() {
    }

    public InvalidTransactionTypeException(String message) {
        super(message);
    }

}
