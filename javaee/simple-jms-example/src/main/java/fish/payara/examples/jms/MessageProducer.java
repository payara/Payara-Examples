/*

 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

 Copyright (c) 2016 C2B2 Consulting Limited. All rights reserved.

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
package fish.payara.examples.jms;

import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.Queue;

/**
 *
 * @author Mike Croft
 */
@Stateless
@LocalBean
public class MessageProducer {


    @Inject
    JMSContext ctx;
    
    @Resource(lookup = "java:global/queue/simpleQ")
    Queue queue;
    
    public void sendMessage(String msg){
        ctx.createProducer().send(queue, msg);
    }
    
    @Schedule(dayOfMonth = "*", dayOfWeek = "*", hour = "*", minute = "*", second = "*/5", persistent = false)
    public void myTimer() {
        sendMessage(new Date().toString());
    }
}
