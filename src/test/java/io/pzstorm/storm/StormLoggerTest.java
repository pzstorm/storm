package io.pzstorm.storm;

import org.apache.logging.log4j.Level;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class StormLoggerTest implements UnitTest {

	private static final String LOGGER_PROPERTY = "storm.logger";

	@Test
	void shouldSetStormLoggerLevelFromSystemProperties() {

		// assert that key does not exist first
		Assertions.assertNull(System.getProperty(LOGGER_PROPERTY));

		Level level = Level.forName("CUSTOM_LEVEL", 1);
		System.setProperty(LOGGER_PROPERTY, level.name());

		// assert that system property was properly set
		String systemProperty = System.getProperty(LOGGER_PROPERTY);
		Assertions.assertEquals(level.name(), systemProperty);

		// assert that logger has correct level
		Assertions.assertEquals(level, StormLogger.get().getLevel());
	}
}
