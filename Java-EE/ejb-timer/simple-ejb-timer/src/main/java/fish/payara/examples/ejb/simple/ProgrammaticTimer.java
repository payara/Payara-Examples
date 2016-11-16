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
package fish.payara.examples.ejb.simple;

import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.TimerService;


/**
 *
 * @author steve
 */
@Singleton
@Startup
public class ProgrammaticTimer {
    
    @Resource
    TimerService ts;
    
    static Logger logger = Logger.getLogger(SimpleTimerBean.class.getCanonicalName());

   @Schedule(hour = "*", minute = "*", second = "*/10", info = "Every 10 second timer")
    public void printSchedule() {
        logger.info("ProgrammaticTimer Schedule Fired .... ");
        ts.createTimer(5000, null);
    }
    
    @Timeout
    public void timeOut() {
        logger.info("Programmatic timeout fired ");
    }
}
