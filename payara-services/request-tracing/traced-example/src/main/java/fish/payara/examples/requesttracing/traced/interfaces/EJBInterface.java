package fish.payara.examples.requesttracing.traced.interfaces;

import javax.ejb.Remote;

/**
 *
 * @author Andrew Pielage
 */
@Remote
public interface EJBInterface {
    void traceMessage(String message);
}
