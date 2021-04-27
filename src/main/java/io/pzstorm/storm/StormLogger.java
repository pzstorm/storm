package io.pzstorm.storm;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

/**
 * <p>Simple wrapper class for logging with Log4j 2 logger.
 * To configure root logger level launch Storm with {@code JVM_PROPERTY} set to a custom logger level.
 * </p><p>
 * Logs will automatically be printed to console and configured log files.
 * Check {@code log4j2.xml} for log file locations.
 * </p>
 * Use the {@code static} methods to print logs with desired log level.
 * If you want to print with a log level not covered by {@code static} methods
 * use {@link #get()} method to get a reference to logger instance.
 */
@SuppressWarnings({ "unused", "WeakerAccess" })
public class StormLogger {

	public static final Level VERBOSE = Level.forName("VERBOSE", 450);
	private static final org.apache.logging.log4j.Logger LOGGER;

	static
	{
		LOGGER = LogManager.getLogger("Storm");
		String sLevel = System.getProperty("storm.logger");
		if (sLevel != null && !sLevel.isEmpty())
		{
			Level level = Level.getLevel(sLevel);
			if (level != null)
			{
				Configurator.setLevel("Storm", level);
				LOGGER.debug("Setting custom level for Storm logger '" + sLevel + '\'');
			}
			else LOGGER.error("Unable to resolve logging level '" + sLevel + '\'');
		}
		LOGGER.debug("Initialized Storm logger");
	}

	/* Make the constructor private to disable instantiation */
	private StormLogger() {
		throw new UnsupportedOperationException();
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
