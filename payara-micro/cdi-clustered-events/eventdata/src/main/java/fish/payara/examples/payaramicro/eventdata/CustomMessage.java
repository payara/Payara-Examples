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
package fish.payara.examples.payaramicro.eventdata;

import java.io.Serializable;

/**
 *
 * @author steve
 */
public class CustomMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    public CustomMessage(String message, String sender) {
        if (message != null && !message.isEmpty()) {
            this.message = message;
        } else {
            this.message = "EMPTY_MESSAGE";
        }

        this.sender = sender;
        this.timeStamp = System.currentTimeMillis();
    }

    public CustomMessage() {
    }

    private String message;
    private String sender;
    private long timeStamp;

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @return the timeStamp
     */
    public long getTimeStamp() {
        return timeStamp;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * @param timeStamp the timeStamp to set
     */
    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

}
