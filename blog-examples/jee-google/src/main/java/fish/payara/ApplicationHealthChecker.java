package fish.payara;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;

import lombok.extern.java.Log;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.*;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

@ApplicationScoped
@Log
public class ApplicationHealthChecker {
	CountDownLatch readinessCountdown = new CountDownLatch(2);
	CountDownLatch startupCountdown = new CountDownLatch(5);
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String APP_STATUS = "app-status";
	@Inject
	@ConfigProperty(name = "free.memory.limit")
	Integer freeMemoryLimit;

	@Produces
	@Liveness
	HealthCheck livenessCheck() {
		String healthCheckName = "hello-jakarta-liveness-health-check";

		return () -> {
			long totalMemory = Runtime.getRuntime().totalMemory();
			log.log(Level.INFO, () -> "Total memory --> " + totalMemory);
			long freeMemory = Runtime.getRuntime().freeMemory();
			log.log(Level.INFO, () -> "Free memory --> " + freeMemory);

			boolean adequateMemory = (((float) freeMemory / totalMemory) * 100) > freeMemoryLimit;
			return adequateMemory ?
					HealthCheckResponse
							.builder()
							.name(healthCheckName)
							.withData(APP_STATUS, "Jakarta EE app is Live")
							.withData("date", LocalDateTime.now(ZoneOffset.UTC)
									.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)))
							.up()
							.build() :
					HealthCheckResponse
							.named(healthCheckName)
							.withData(APP_STATUS, "Jakarta EE app is NOT Live... Yet?")
							.withData("date", LocalDateTime.now(ZoneOffset.UTC)
									.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)))
							.down()
							.build();

		};
	}

	@Produces
	@Readiness
	HealthCheck readinessCheck() {
		return () -> {
			readinessCountdown.countDown();
			return readinessCountdown.getCount() == 0 ?
					HealthCheckResponse
							.named("jakarta-readiness")
							.up()
							.withData(APP_STATUS, "Jakarta EE app is Ready!")
							.withData("date", LocalDateTime.now(ZoneOffset.UTC)
									.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)))
							.build() :
					HealthCheckResponse
							.named("jakarta-readiness")
							.down()
							.withData(APP_STATUS, "Jakarta EE app is NOT Ready!")
							.withData("date", LocalDateTime.now(ZoneOffset.UTC)
									.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)))
							.build();
		};
	}

	@Produces
	@Startup
	HealthCheck startupCheck() {
		return () -> {
			startupCountdown.countDown();
			return startupCountdown.getCount() == 0 ?
					HealthCheckResponse
							.named("jakarta-app-started")
							.up()
							.withData(APP_STATUS, "Jakarta EE app has started!")
							.withData("date", LocalDateTime.now(ZoneOffset.UTC)
									.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)))
							.build() :
					HealthCheckResponse
							.named("jakarta-app-startup-failed")
							.down()
							.withData(APP_STATUS, "Jakarta EE app startup failed!!")
							.withData("date", LocalDateTime.now(ZoneOffset.UTC)
									.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)))
							.build();
		};
	}

}
