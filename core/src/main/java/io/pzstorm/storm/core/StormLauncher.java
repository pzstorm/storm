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

package io.pzstorm.storm.core;

import java.lang.reflect.Method;

import io.pzstorm.storm.logging.StormLogger;

class StormLauncher {

	/**
	 * Class name of Project Zomboid application entry point. This is the first class
	 * loaded by {@link StormClassLoader} which in turn loads all game classes.
	 */
	private static final String ZOMBOID_ENTRY_POINT_CLASS = "zombie.gameStates.MainScreenState";

	/**
	 * Name of the method that is the entry point to Project Zomboid execution.
	 * This will be invoked through reflection from {@link #main(String[])} to launch the game
	 */
	private static final String ZOMBOID_ENTRY_POINT = "main";

	/**
	 * Launch Project Zomboid with given array or arguments.
	 *
	 * @param args array of arguments to use to launch the game.
	 *
	 * @throws ReflectiveOperationException if loading or invoking entry point failed.
	 */
	public static void main(String[] args) throws ReflectiveOperationException {

		// initialize logging system
		StormLogger.initialize();

		StormLogger.debug("Preparing to launch Project Zomboid...");
		StormClassLoader classLoader = StormBootstrap.CLASS_LOADER;

		Class.forName("io.pzstorm.storm.core.StormClassTransformers", true, classLoader);
		Class.forName("io.pzstorm.storm.logging.ZomboidLogger", true, classLoader);

		// redirect uncaught exception logs to Log4J
		Thread.setDefaultUncaughtExceptionHandler(new StormLogger.Log4JUncaughtExceptionHandler());

		// load and register all local mods found in user.home
		StormBootstrap.loadAndRegisterMods();

		// load LuaEvent factory and classes
		classLoader.loadClass("io.pzstorm.storm.event.LuaEventFactory");

		// initialize dispatcher system
		Class<?> eventHandler = classLoader.loadClass("io.pzstorm.storm.event.StormEventHandler");
		Class<?> eventDispatcher = classLoader.loadClass("io.pzstorm.storm.event.StormEventDispatcher");
		eventDispatcher.getDeclaredMethod("registerEventHandler", Class.class).invoke(null, eventHandler);

		Class<?> entryPointClass = classLoader.loadClass(ZOMBOID_ENTRY_POINT_CLASS);
		Method entryPoint = entryPointClass.getMethod(ZOMBOID_ENTRY_POINT, String[].class);
		try {
			/* we invoke the entry point using reflection because we don't want to reference
			 * the entry point class which would to the class being loaded by application class loader
			 */
			StormLogger.debug("Launching Project Zomboid...");
			entryPoint.invoke(null, (Object) args);
		}
		catch (Throwable e)
		{
			StormLogger.error("An unhandled exception occurred while running Project Zomboid");
			throw new RuntimeException(e);
		}
	}
}
