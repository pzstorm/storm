package io.pzstorm.storm.util;

import io.pzstorm.storm.core.StormLogger;

import java.lang.reflect.Method;

public class StormUtils {

	/**
	 * Returns a path representation of the name for given {@code Class}.
	 *
	 * @param clazz {@code Class} whose name should be converted to path.
	 * @return {@code String} representing a path.
	 */
	public static String getClassAsPath(Class<?> clazz) {
		return clazz.getName().replace('.', '/');
	}

	/**
	 * Invokes the given inaccessible {@code Method} without leaving method accessible.
	 * After the method was invoked the {@code accessible} flag will be set to it's original state.
	 *
	 * @param method inaccessible {@code Method} to invoke.
	 * @param obj the object the underlying method is invoked from.
	 * @param args the arguments used for the method call.
	 *
	 * @throws ReflectiveOperationException if an exception occurred while invoking method.
	 */
	public static void invokeRestrictedMethod(Method method, Object obj, Object...args) throws ReflectiveOperationException {

		boolean wasAccessible = false;
		if (method.isAccessible())
		{
			StormLogger.warn("Calling 'invokeRestrictedMethod' to invoker accessible method.");
			wasAccessible = true;
		}
		else method.setAccessible(true);

		method.invoke(obj, args);
		// set accessibility state to original
		if (!wasAccessible) {
			method.setAccessible(false);
		}
	}
}
