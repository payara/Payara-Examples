/*

 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

 Copyright (c) 2015 C2B2 Consulting Limited. All rights reserved.

 The contents of this file are subject to the terms of the Common Development
 and Distribution License("CDDL") (collectively, the "License").  You
 may not use this file except in compliance with the License.  You can
 obtain a copy of the License at
 https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 or packager/legal/LICENSE.txt.  See the License for the specific
 language governing permissions and limitations under the License.

 When distributing the software, include this License Header Notice in each
 file and include the License file at packager/legal/LICENSE.txt.
 */
package fish.payara.examples.payaramicro.event.receiver;

import fish.payara.examples.payaramicro.eventdata.CustomMessage;
import fish.payara.micro.cdi.Inbound;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

/**
 * An Application Scoped CDI Bean that receives clustered CDI events
 * @author steve
 */
@ApplicationScoped
public class MessageReceiverBean {
    
    private List<CustomMessage> messagesReceived;

    /**
     * Observer method that receives events Inbound from the cluster to the server
     * You must use the @Inbound annotation to receive cluster events
     * @param event 
     */
    public void observe(@Observes @Inbound CustomMessage event) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "MessageReceiverBean Received Event {0}", event);
        messagesReceived.add(event);
    }

    public List<CustomMessage> getMessagesReceived() {
        return messagesReceived;
    }

    void init() {
        Logger.getLogger(this.getClass().getName()).info("MessageReceiverBean initialised");
        messagesReceived = new LinkedList<>();
    }
    
    
}
