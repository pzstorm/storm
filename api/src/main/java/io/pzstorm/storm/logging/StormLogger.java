/*
 * Zomboid Storm - Java modding toolchain for Project Zomboid
 * Copyright (C) 2021 Matthew Cain
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package io.pzstorm.storm.logging;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

/**
 * <p>Wrapper class for printing Storm logs with Log4j 2 logger.
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
				rootLoggerConfig.removeAppender("StormConsole");
				rootLoggerConfig.addAppender(config.getAppender("StormConsole"), level, null);

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
}
