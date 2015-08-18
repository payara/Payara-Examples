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
package fish.payara.examples.payaramicro.autobind.cluster;

import fish.payara.micro.BootstrapException;
import fish.payara.micro.PayaraMicro;
import fish.payara.micro.PayaraMicroRuntime;
import fish.payara.micro.services.PayaraClusterListener;
import fish.payara.micro.services.data.InstanceDescriptor;

/**
 *
 * @author steve
 */
public class ClusteredPayaraMicro implements PayaraClusterListener {
    
    public static void main(String[] args) throws BootstrapException {
        PayaraMicroRuntime runtime = PayaraMicro.getInstance()
                .setHttpAutoBind(true)
                .setInstanceName("TestInstance")
                .bootStrap();
        runtime.addClusterListener(new ClusteredPayaraMicro());
    }

    @Override
    public void memberAdded(InstanceDescriptor id) {
        System.out.println("Payara Micro Instance " + id.getMemberUUID() + " has Appeared " + " on Host and http Port " + id.getHostName() + ":" + id.getHttpPort());
    }

    @Override
    public void memberRemoved(InstanceDescriptor id) {
        System.out.println("Payara Micro Instance " + id.getMemberUUID() + " has Disappeared " + " on Host and http Port " + id.getHostName() + ":" + id.getHttpPort());
    }
    
}
