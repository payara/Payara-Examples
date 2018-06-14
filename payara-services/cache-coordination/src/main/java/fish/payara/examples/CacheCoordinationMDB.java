/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) [2018] Payara Foundation and/or its affiliates. All rights reserved.
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
package fish.payara.examples;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.BytesMessage;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.eclipse.persistence.internal.sessions.AbstractSession;
import org.eclipse.persistence.internal.sessions.coordination.jms.JMSTopicRemoteConnection;
import org.eclipse.persistence.sessions.coordination.Command;
import org.eclipse.persistence.sessions.coordination.RemoteCommandManager;
import org.eclipse.persistence.sessions.serializers.JavaSerializer;
import org.eclipse.persistence.sessions.serializers.Serializer;
import org.eclipse.persistence.sessions.server.ServerSession;

/**
 * Cache Coordination MDB example for EclipseLink 2.5+
 * This demonstrates application with two Persistence Units, DatabaseOne and DatabaseTwo
 * MDB will figure out which database the message goes to by channel name
 *
 * @author lprimak
 */
@MessageDriven(activationConfig = {
            @ActivationConfigProperty(propertyName = "destinationLookup",
                                      propertyValue = "jms/EclipseLinkTopic"),
            @ActivationConfigProperty(propertyName = "destinationType",
                                      propertyValue = "javax.jms.Topic"),
            @ActivationConfigProperty(propertyName = "useSharedSubscriptionInClusteredContainer",
                                      propertyValue = "false")
        })
public class CacheCoordinationMDB implements MessageListener {
    private final Map<String, JMSTopicRemoteConnection> serviceMap = new HashMap<>();

    private @Inject JMSContext context;
    private static final Logger log = Logger.getLogger(CacheCoordinationMDB.class.getName());


    private @PersistenceContext(unitName = "DatabaseOne") EntityManager databaseOne;
    private @PersistenceContext(unitName = "DatabaseTwo") EntityManager databaseTwo;


    /**
     * For any additional databases in the application, a line needs to be present in @PostConstruct and
     * the persistence unit above
     */
    @PostConstruct
    public void init()
    {
        serviceMap.put(getRCM(databaseOne).getServiceId().getChannel(), new JMSTopicRemoteConnection(getRCM(databaseOne)));
        serviceMap.put(getRCM(databaseTwo).getServiceId().getChannel(), new JMSTopicRemoteConnection(getRCM(databaseTwo)));
    }

    
    @Override
    public void onMessage(Message message)
    {
        try {
            ObjectMessage outMsg = transformIncoming(message);
            if (outMsg == null)
            {
                log.log(Level.WARNING, "Can''t Translate Message: {0}", message.toString());
                return;
            }
            
            String channelId = getChannel(outMsg);
            if (channelId == null)
            {
                log.log(Level.WARNING, "Can''t find Channel Id for Message: {0}", outMsg.getObject());
                return;
            }
            log.log(Level.FINE, "Invalidating EL Cache for channelID: {0}", channelId);
            JMSTopicRemoteConnection channel = serviceMap.get(channelId);

            if (channel != null)
            {
                channel.onMessage(outMsg);
            }
            else
            {
                log.log(Level.WARNING, "Can''t find Channel Id Locally: {0}", channelId);
            }
        } catch (JMSException ex) {
            log.log(Level.SEVERE, "Cache Coordination JMS Failure", ex);
        }
    }


    private String getChannel(ObjectMessage message) throws JMSException
    {
        return ((Command)message.getObject()).getServiceId().getChannel();
    }

    private ObjectMessage transformIncoming(Message message) throws JMSException
    {
        BytesMessage byteMessage = (BytesMessage) message;
        byte[] bytes = new byte[(int) byteMessage.getBodyLength()];
        byteMessage.readBytes(bytes);
        Serializable cmd = deserialize(bytes, getRCM(databaseOne)); // any valid PU will do here
        if(cmd != null)
        {
            return context.createObjectMessage(cmd);
        }
        else
        {
            return null;
        }
    }

    private static Serializable deserialize(byte[] bytes, RemoteCommandManager rcm)
    {
        Serializer serializer = rcm.getSerializer();
        if (serializer == null)
        {
            serializer = JavaSerializer.instance;
        }
        return (Serializable) serializer.deserialize(bytes, (AbstractSession) rcm.getCommandProcessor());
    }

    private RemoteCommandManager getRCM(EntityManager em)
    {
        return (RemoteCommandManager)em.unwrap(ServerSession.class).getCommandManager();
    }
}
