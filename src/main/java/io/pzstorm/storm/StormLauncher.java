package io.pzstorm.storm;

import java.lang.reflect.Method;

import zombie.debug.DebugLog;

class StormLauncher {

	/**
	 * Class name of Project Zomboid application entry point. This is the first class
	 * loaded by {@link StormClassLoader} which in turn loads all game classes.
	 */
	private static final String ZOMBOID_ENTRY_POINT_CLASS = "zombie.gameStates.MainScreenState";

	/**
	 * Name of the method that is the entry point to Project Zomboid execution.
	 * This will be invoked through reflection from {@link #launch(String[])} to launch the game
	 */
	private static final String ZOMBOID_ENTRY_POINT = "main";

	/**
	 * Launch Project Zomboid with given array or arguments.
	 *
	 * @param args array of arguments to use to launch the game.
	 *
	 * @throws ReflectiveOperationException if loading or invoking entry point failed.
	 */
	static void launch(String[] args) throws ReflectiveOperationException {

		StormClassLoader stormLoader = new StormClassLoader();
		Class<?> entryPointClass = stormLoader.loadClass(ZOMBOID_ENTRY_POINT_CLASS);
		Method entryPoint = entryPointClass.getMethod(ZOMBOID_ENTRY_POINT, String[].class);
		try {
			/* we invoke the entry point using reflection because we don't want to reference
			 * the entry point class which would to the class being loaded by application class loader
			 */
			entryPoint.invoke(null, (Object) args);
		}
		catch (Throwable e)
		{
			DebugLog.General.error("An unhandled exception occurred while running Project Zomboid");
			throw new RuntimeException(e);
		}
	}
}
