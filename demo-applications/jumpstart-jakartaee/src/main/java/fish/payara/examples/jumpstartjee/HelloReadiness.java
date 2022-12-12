/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
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
package fish.payara.examples.jumpstartjee;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;

import lombok.extern.java.Log;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
@Readiness
@Log
public class HelloReadiness implements HealthCheck {

	@Inject
	@ConfigProperty(name = "free.memory.limit")
	Integer freeMemoryLimit;
	static final String HEALTH_CHECK_NAME = "jakarta-readiness";

	@Override
	public HealthCheckResponse call() {
		long totalMemory = Runtime.getRuntime().totalMemory();
		log.log(Level.INFO, () -> "Total memory --> " + totalMemory);
		long freeMemory = Runtime.getRuntime().freeMemory();
		log.log(Level.INFO, () -> "Free memory --> " + freeMemory);

		boolean adequateMemory = (((float) freeMemory / totalMemory) * 100) > freeMemoryLimit;
		return adequateMemory ?
				HealthCheckResponse
						.builder()
						.name(HEALTH_CHECK_NAME)
						.up()
						.withData("app-status", "Jakarta EE app is ready")
						.withData("date", LocalDateTime.now(ZoneOffset.UTC)
								.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
						.up()
						.build() :
				HealthCheckResponse
						.named(HEALTH_CHECK_NAME)
						.withData("app-status", "Jakarta EE app is NOT ready... Yet?")
						.withData("reason", "Available memory is less than 40%")
						.down()
						.withData("date", LocalDateTime.now(ZoneOffset.UTC)
								.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
						.down()
						.build();
	}
}
