/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2017 Payara Foundation and/or its affiliates. All rights reserved.
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
package fish.payara.examples.microprofile.configinjection;

import java.time.LocalDate;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author steve
 */
@RequestScoped
public class TestBean {
        
    @Inject
    @ConfigProperty(name = "default.property", defaultValue = "Default Value")
    String defaultProperty;
    
    // Example showing reading a config property from the META-INF/microprofile-config.properties file
    @Inject
    @ConfigProperty(name = "file.property")
    String fileProperty;
    
    // Example config property that uses a default converter to LocalDate
    @Inject
    @ConfigProperty(name = "date.property")
    LocalDate date;

    // Example injection the standard environment variable home
    @Inject
    @ConfigProperty(name = "HOME", defaultValue = "HOME environment variable not set")
    String home;
    
    // Example injection of the standard System Property java.home
    @Inject
    @ConfigProperty(name = "java.home", defaultValue = "java.home environment variable not set")
    String javaHome;
    
// Example showing reading a config property from the META-INF/microprofile-config.properties file
    @Inject
    @ConfigProperty(name = "application.property")
    String applicationProperty;
    

    public String getDefaultProperty() {
        return defaultProperty;
    }

    public String getFileProperty() {
        return fileProperty;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getHome() {
        return home;
    }

    public String getJavaHome() {
        return javaHome;
    }

    public String getApplicationProperty() {
        return applicationProperty;
    }


    
}
