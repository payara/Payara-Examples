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
package fish.payara.examples.payaramicro.clustereddeployexample;

import fish.payara.micro.BootstrapException;
import fish.payara.micro.PayaraMicro;
import fish.payara.micro.PayaraMicroRuntime;
import fish.payara.micro.services.command.ClusterCommandResult;
import fish.payara.micro.services.data.InstanceDescriptor;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * This example class deploys the clustered event bus example wars across a
 * running Payara Micro cluster
 *
 * @author steve
 */
public class ClusteredDeploy {

    public static void main(String[] args) throws BootstrapException, InterruptedException, ExecutionException {
        
        // Boot and join the Payara Micro Cluster
        PayaraMicroRuntime runtime = PayaraMicro.getInstance().setHttpAutoBind(true).bootStrap();
        
        // Deploy the sample event-sender application across the cluster
        Map<InstanceDescriptor, Future<ClusterCommandResult>> run = runtime.run("deploy","--force", "../event-sender/target/event-sender-1.0-SNAPSHOT.war");
        for (Future<ClusterCommandResult> future : run.values()) {
            System.out.println(future.get().getOutput());
        }

        // Deploy the sample event-receiver application across the cluster
       run = runtime.run("deploy", "--force", "../event-receiver/target/event-receiver-1.0-SNAPSHOT.war");
       for (Future<ClusterCommandResult> future : run.values()) {
            System.out.println(future.get().getOutput());
       }

        Thread.sleep(10000);
        runtime.shutdown();
    }

}
