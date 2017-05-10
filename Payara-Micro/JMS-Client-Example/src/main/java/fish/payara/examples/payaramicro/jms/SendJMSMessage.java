/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) [2016-2017] Payara Foundation and/or its affiliates. All rights reserved.
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
package fish.payara.examples.payaramicro.jms;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;
import javax.resource.AdministeredObjectDefinition;
import javax.resource.ConnectionFactoryDefinition;

/**
 * An example Timer Bean to send messages to an ActiveMQ broker
 * 
 * @author Steve Millidge <Payara Services Limited>
 */
@Stateless
@ConnectionFactoryDefinition ( name = "java:global/jms/SendJMS",
        interfaceName = "javax.jms.ConnectionFactory",
        resourceAdapter = "activemq-rar-5.14.5",
        properties = {"UserName=admin","Password=admin","ServerUrl=tcp://127.0.0.1:61616"})

@AdministeredObjectDefinition ( resourceAdapter = "activemq-rar-5.14.5",
        interfaceName = "javax.jms.Queue",
        className = "org.apache.activemq.command.ActiveMQQueue",
        name = "java:global/jms/TestQ",
        properties = {"PhysicalName=TESTQ"})
public class SendJMSMessage {
    
    @Resource(lookup = "java:global/jms/TestQ")
    Queue queue;
    
    @Resource(lookup = "java:global/jms/SendJMS")
    ConnectionFactory factory;

    @Schedule(hour = "*", minute = "*", second = "*/5", info = "Every 5 second timer", timezone = "UTC", persistent = false)
    public void myTimer() {
        try (Connection conn = factory.createConnection()){
            Session sess = conn.createSession(true,Session.AUTO_ACKNOWLEDGE);
            sess.createProducer(queue).send(sess.createTextMessage("This is a test at " + new Date()));
        } catch (JMSException ex) {
            Logger.getLogger(SendJMSMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
