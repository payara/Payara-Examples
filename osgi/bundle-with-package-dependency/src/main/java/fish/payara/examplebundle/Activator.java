package fish.payara.examplebundle;

import java.util.logging.Logger;
import org.eclipse.persistence.expressions.ExpressionBuilder;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {
        Logger.getLogger(this.getClass().getName()).info("My Payara-Example bundle activated");
        ExpressionBuilder eb = new ExpressionBuilder();
        Logger.getLogger(this.getClass().getName()).info("Created ExpressionBuilder " + eb);
    }

    public void stop(BundleContext context) throws Exception {
        // TODO add deactivation code here
    }

}
