package io.pzstorm.storm.logging;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

/**
 * Wrapper class for printing Project Zomboid logs with Log4j 2 logger.
 */
@SuppressWarnings({ "unused", "WeakerAccess" })
public class ZomboidLogger {

	private static final Logger LOGGER = LogManager.getLogger("Zomboid");

	/**
	 * Returns an instance of Log4j {@link Logger} used for logging.
	 */
	public static Logger get() {
		return LOGGER;
	}

	/**
	 * Logs a message object with the {@link Level#INFO INFO} level.
	 */
	public static void info(String log) {
		LOGGER.info(log);
	}

	/**
	 * Logs a formatted message with {@link Level#INFO INFO}
	 * level using the specified format string and arguments.
	 *
	 * @param format the format {@code String}.
	 * @param params arguments specified by the format.
	 */
	public static void info(String format, Object... params) {
		LOGGER.printf(Level.INFO, format, params);
	}

	/**
	 * Logs a message object with the {@link StormLogger#VERBOSE VERBOSE} level.
	 */
	public static void detail(String log) {
		LOGGER.log(StormLogger.VERBOSE, log);
	}

	/**
	 * Logs a formatted message with {@link StormLogger#VERBOSE VERBOSE}
	 * level using the specified format string and arguments.
	 *
	 * @param format the format {@code String}.
	 * @param params arguments specified by the format.
	 */
	public static void detail(String format, Object... params) {
		LOGGER.printf(StormLogger.VERBOSE, format, params);
	}

	/**
	 * Logs a message object with the {@link Level#ERROR ERROR} level.
	 *
	 * @param message the message string to log.
	 */
	public static void error(String message) {
		LOGGER.error(message);
	}

	/**
	 * Logs a formatted message with {@link Level#ERROR ERROR}
	 * level using the specified format string and arguments.
	 *
	 * @param format the format {@code String}.
	 * @param params arguments specified by the format.
	 */
	public static void error(String format, Object... params) {
		LOGGER.printf(Level.ERROR, format, params);
	}

	/**
	 * Logs a message at the {@link Level#ERROR ERROR} level including the
	 * stack trace of the {@link Throwable} <code>t</code> passed as parameter.
	 *
	 * @param message the message object to log.
	 * @param t the exception to log, including its stack trace.
	 */
	public static void error(String message, Throwable t) {
		LOGGER.error(message, t);
	}

	/**
	 * Logs a message object with the {@link Level#WARN WARN} level.
	 *
	 * @param message the message string to log.
	 */
	public static void warn(String message) {
		LOGGER.warn(message);
	}

	/**
	 * Logs a formatted message with {@link Level#WARN WARN}
	 * level using the specified format string and arguments.
	 *
	 * @param format the format {@code String}.
	 * @param params arguments specified by the format.
	 */
	public static void warn(String format, Object... params) {
		LOGGER.printf(Level.WARN, format, params);
	}

	/**
	 * Logs a message object with the {@link Level#DEBUG DEBUG} level.
	 *
	 * @param message the message string to log.
	 */
	public static void debug(String message) {
		LOGGER.debug(message);
	}

	/**
	 * Logs a formatted message with {@link Level#DEBUG DEBUG}
	 * level using the specified format string and arguments.
	 *
	 * @param format the format {@code String}.
	 * @param params arguments specified by the format.
	 */
	public static void debug(String format, Object... params) {
		LOGGER.printf(Level.DEBUG, format, params);
	}

	/**
	 * Logs a message at the {@link Level#DEBUG DEBUG} level including the
	 * stack trace of the {@link Throwable} <code>t</code> passed as parameter.
	 *
	 * @param message the message to log.
	 * @param t the exception to log, including its stack trace.
	 */
	public static void debug(String message, Throwable t) {
		LOGGER.debug(message, t);
	}

	/**
	 * Logs a formatted message using the specified format string and arguments.
	 *
	 * @param level The logging Level.
	 * @param format The format String.
	 * @param params Arguments specified by the format.
	 */
	public static void printf(Level level, String format, Object... params) {
		LOGGER.printf(level, format, params);
	}

	/**
	 * Logs a message at the designated {@link LogSeverity LogSeverity}.
	 *
	 * @param level {@link LogSeverity LogSeverity} formatted as {@code String}.
	 * @param message the message to log.
	 */
	public static void printf(String level, String message) {
		LOGGER.printf(LogSeverity.getForName(level, LogSeverity.GENERAL), message);
	}

	/**
	 * This enum represents logging levels used by Project Zomboid.
	 */
	private enum LogSeverity {

		TRACE("Trace", Level.TRACE),
		GENERAL("General", Level.INFO),
		WARNING("Warning", Level.WARN),
		ERROR("Error", Level.ERROR);

		/**
		 * Name of the entry that matches the value of Project Zomboid {@code LogSeverity} enum entry
		 * level exactly. It is important the entry matches exactly because the game enum entries are
		 * converted to string to communicate at which level should the logging event be printed at.
		 */
		private final String name;

		/**
		 * Log4j 2 level associated with this {@link LogSeverity} entry.
		 */
		private final Level level;

		LogSeverity(String name, Level level) {

			this.name = name;
			this.level = level;
		}

		/**
		 * Returns {@link Level} associated with a {@link LogSeverity LogSeverity}
		 * entry matched by given name. If no entry was found a level associated
		 * with a specified default {@code LogSeverity} entry will be used instead.
		 *
		 * @param name name of the {@code LogSeverity} entry to get the level for.
		 * @param def default {@code LogSeverity} entry to use if no entry was matched.
		 *
 		 * @return {@code Level} associated with matched entry or {@code Level} associated
		 * 		with default entry if no entry was matched for given name.
		 */
		private static Level getForName(String name, LogSeverity def) {
			return Arrays.stream(values()).filter(l -> l.name.equals(name)).findFirst().orElse(def).level;
		}
	}
}
