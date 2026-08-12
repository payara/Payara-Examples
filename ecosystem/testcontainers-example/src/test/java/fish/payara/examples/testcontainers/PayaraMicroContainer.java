/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2026 Payara Foundation and/or its affiliates. All rights reserved.
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
package fish.payara.examples.testcontainers;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

/**
 * A Testcontainers {@link GenericContainer} pre-configured to run Payara Micro
 * with the application WAR deployed.
 */
public class PayaraMicroContainer extends GenericContainer<PayaraMicroContainer> {

    private static final int DEFAULT_PORT = 8080;
    private static final String DEFAULT_CONTEXT_PATH = "/";
    protected static final String CONTEXT = "ObservabilityTool";

    public PayaraMicroContainer() {
        this(DockerImageName.parse("payara/micro:" + requiredProperty("payara.version")));
        withDeploymentPath(requiredProperty("war.path"));
    }

    public PayaraMicroContainer(DockerImageName dockerImageName) {
        super(dockerImageName);
        withExposedPorts(DEFAULT_PORT);
        waitingFor(Wait.forLogMessage(".*Payara Micro .* ready.*\\n", 1));
    }

    public PayaraMicroContainer withDeploymentPath(String warPath) {
        withCopyFileToContainer(
            MountableFile.forHostPath(warPath),
            "/opt/payara/deployments/application.war"
        );
        return this;
    }

    public String getApplicationUrl() {
        return String.format(
            "http://%s:%d%s",
            getHost(),
            getMappedPort(DEFAULT_PORT),
            DEFAULT_CONTEXT_PATH
        );
    }

    private static String requiredProperty(String name) {
        String value = System.getProperty(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                "System property '" + name + "' is not set. It must be supplied by the Maven build "
                    + "(see the failsafe plugin's systemPropertyVariables in pom.xml).");
        }
        return value;
    }
}