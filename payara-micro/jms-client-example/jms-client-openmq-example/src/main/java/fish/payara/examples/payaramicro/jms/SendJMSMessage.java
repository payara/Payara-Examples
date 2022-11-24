/*
 * DO NOT ALTER OR REMOTE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2022 Payara Foundation and/or its affiliates. All rights reserved.
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
import jakarta.annotation.Resource;
import jakarta.ejb.Schedule;
import jakarta.ejb.Stateless;
import jakarta.jms.*;
import jakarta.resource.AdministeredObjectDefinition;
import jakarta.resource.ConnectionFactoryDefinition;

/**
 * An example Timer Bean to send messages to an ActiveMQ broker
 * 
 * @author Steve Millidge <Payara Services Limited>
 */
@Stateless
@ConnectionFactoryDefinition ( name = "java:app/jms/SendJMS",
        interfaceName = "jakarta.jms.ConnectionFactory",
        resourceAdapter = "imqjmsra",
        properties = {"UserName=${MPCONFIG=mq.username}","Password=${MPCONFIG=mq.password}", "AddressList=${MPCONFIG=mq.addressList}"})

@AdministeredObjectDefinition ( resourceAdapter = "imqjmsra",
        interfaceName = "jakarta.jms.Queue",
        className = "com.sun.messaging.Queue",
        name = "java:app/jms/TestQ",
        properties = {"Name=${MPCONFIG=mq.queue.name}"})
public class SendJMSMessage {
    
    @Resource(lookup = "java:app/jms/TestQ")
    Queue queue;
    
    @Resource(lookup = "java:app/jms/SendJMS")
    ConnectionFactory factory;

    @Schedule(hour = "*", minute = "*", second = "*/5", info = "Every 5 second timer", timezone = "UTC", persistent = false)
    public void myTimer() {
        try (JMSContext context = factory.createContext()){
            context.createProducer().send(queue, "This is a test at " + new Date());
        } catch (JMSRuntimeException ex) {
            Logger.getLogger(SendJMSMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
