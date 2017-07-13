/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) [2017] Payara Foundation and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://github.com/payara/Payara/blob/master/LICENSE.txt
 * See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * The Payara Foundation designates this particular file as subject to the "Classpath"
 * exception as provided by the Payara Foundation in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package fish.payara.examples.jms11notificationconsumer;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.resource.AdministeredObjectDefinition;
import javax.resource.ConnectionFactoryDefinition;

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
@ConnectionFactoryDefinition(name = "java:global/jms/myConnectionPool",
        interfaceName = "javax.jms.ConnectionFactory",
        resourceAdapter = "activemq-rar-5.14.5",
        properties = {"UserName=admin", "Password=admin", "ServerUrl=tcp://127.0.0.1:61616"
        })
@AdministeredObjectDefinition(resourceAdapter = "activemq-rar-5.14.5",
        interfaceName = "javax.jms.Queue",
        className = "org.apache.activemq.command.ActiveMQQueue",
        name = "java:global/jms/notifierQueue",
        properties = {"PhysicalName=notifierQueue"
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
