package fish.payara.examples.jms11notificationconsumer;


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author Mike Croft
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/notifierQueue"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "notifierQueue"),
    @ActivationConfigProperty(propertyName = "resourceAdapter", propertyValue = "activemq-rar-5.14.5")

})
public class JMS11NotificationConsumer implements MessageListener {

    public JMS11NotificationConsumer() {
    }

    @Override
    public void onMessage(Message message) {
        try {
            Thread.sleep(1000L);

            // JMS 1.1 for ActiveMQ
            if (message instanceof TextMessage) {
                TextMessage txtMsg = (TextMessage) message;
                 System.out.println("Read Message: " + txtMsg.getText());
            }

        } catch (Exception ex) {
            Logger.getLogger(JMS11NotificationConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}