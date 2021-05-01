package io.pzstorm.storm;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.AppenderControl;
import org.apache.logging.log4j.core.config.AppenderControlArraySet;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StormLoggerTest implements UnitTest {

	private static final String LOGGER_PROPERTY = "storm.logger";

	@Test
	void shouldSetStormLoggerLevelFromSystemProperties() throws ReflectiveOperationException {

		// store current level from system properties
		Level originalLevel = Level.toLevel(System.getProperty(LOGGER_PROPERTY, "INFO"));

		Level expectedLevel = Level.forName("ALL", 1);
		System.setProperty(LOGGER_PROPERTY, expectedLevel.name());

		// assert that system property was properly set
		String systemProperty = System.getProperty(LOGGER_PROPERTY);
		Assertions.assertEquals(expectedLevel.name(), systemProperty);

		// initialize StormLogger class
		Class.forName("io.pzstorm.storm.StormLogger");

		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		LoggerConfig rootLoggerConfig = ctx.getConfiguration().getLoggers().get("");
		Configuration config = ctx.getConfiguration();

		Field appendersField = LoggerConfig.class.getDeclaredField("appenders");
		appendersField.setAccessible(true);

		Field appenderLevelField = AppenderControl.class.getDeclaredField("level");
		appenderLevelField.setAccessible(true);

		Map<String, Level> actualLevels = new HashMap<>();

		AppenderControlArraySet appenders = (AppenderControlArraySet) appendersField.get(rootLoggerConfig);
		for (AppenderControl appenderControl : appenders.get())
		{
			String appenderName = appenderControl.getAppenderName();
			if (appenderName.equals("Console") || appenderName.equals("MainFile")) {
				actualLevels.put(appenderName, (Level) appenderLevelField.get(appenderControl));
			}
		}
		// assert that all appenders were found
		Assertions.assertTrue(actualLevels.containsKey("Console"));
		Assertions.assertTrue(actualLevels.containsKey("MainFile"));

		// assert that logger has correct levels
		for (Map.Entry<String, Level> entry : actualLevels.entrySet()) {
			Assertions.assertEquals(expectedLevel, entry.getValue());
		}
		// reset the logger levels to original values
		for (String appender : new String[]{ "Console", "MainFile" })
		{
			rootLoggerConfig.removeAppender(appender);
			rootLoggerConfig.addAppender(config.getAppender(appender), originalLevel, null);
		}
		ctx.updateLoggers();
	}
}
