package io.pzstorm.storm.logging;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

/**
 * <p>Simple wrapper class for logging with Log4j 2 logger.
 * To configure console logging level launch Storm with {@code JVM_PROPERTY}
 * set to a custom logger level and call {@link #initialize()} method.
 * </p><p>
 * Logs will automatically be printed to console and configured log files.
 * Check {@code log4j2.xml} for log file locations. Use the {@code static} methods to
 * print logs with desired log level. If you want to print with a log level not covered
 * by {@code static} methods use {@link #get()} method to get a reference to logger instance.
 */
@SuppressWarnings({ "unused", "WeakerAccess" })
public class StormLogger {

	public static final Level VERBOSE = Level.forName("VERBOSE", 450);
	static final String LOGGER_PROPERTY = "storm.logger";
	private static final Logger LOGGER = LogManager.getLogger("Storm");

	/* Make the constructor private to disable instantiation */
	private StormLogger() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Initialize {@link StormLogger} system by setting logging level resolved from system properties.
	 * To configure console logging level launch Storm with {@code JVM_PROPERTY} set to a custom logger level.
	 */
	public static void initialize() {

		String sLevel = System.getProperty(LOGGER_PROPERTY);
		if (sLevel != null && !sLevel.isEmpty())
		{
			Level level = Level.getLevel(sLevel);
			if (level != null)
			{
				LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
				Configuration config = ctx.getConfiguration();

				LoggerConfig rootLoggerConfig = config.getLoggers().get("");
				for (String appender : new String[] { "Console", "MainFile" })
				{
					rootLoggerConfig.removeAppender(appender);
					rootLoggerConfig.addAppender(config.getAppender(appender), level, null);
				}
				ctx.updateLoggers();
				LOGGER.info("Setting custom level for Storm logger '" + sLevel + '\'');
			}
			else LOGGER.error("Unable to resolve logging level '" + sLevel + '\'');
		}
		LOGGER.info("Initialized Storm logger");
	}

	/**
	 * Returns an instance of Log4j {@link Logger} used for logging.
	 */
	public static Logger get() {
		return LOGGER;
	}

	public static void info(String log) {
		LOGGER.info(log);
	}

	public static void info(String log, Object... params) {
		LOGGER.printf(Level.INFO, log, params);
	}

	public static void detail(String log) {
		LOGGER.log(VERBOSE, log);
	}

	public static void detail(String log, Object... params) {
		LOGGER.printf(VERBOSE, log, params);
	}

	public static void error(String log) {
		LOGGER.error(log);
	}

	public static void error(String log, Object... args) {
		LOGGER.printf(Level.ERROR, log, args);
	}

	public static void error(String log, Throwable t) {
		LOGGER.error(log, t);
	}

	public static void warn(String log) {
		LOGGER.warn(log);
	}

	public static void warn(String format, Object... params) {
		LOGGER.printf(Level.WARN, format, params);
	}

	public static void debug(String log) {
		LOGGER.debug(log);
	}

	public static void debug(String format, Object... args) {
		LOGGER.printf(Level.DEBUG, format, args);
	}

	public static void debug(String log, Throwable t) {
		LOGGER.debug(log, t);
	}

	public static void printf(Level level, String format, Object... params) {
		LOGGER.printf(level, format, params);
	}
}
