package io.pzstorm.storm;

import io.pzstorm.storm.logging.StormLogger;

import java.lang.reflect.Method;

public class TestUtils {

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
	public static void invokeRestrictedMethod(Method method, Object obj, Object... args) throws ReflectiveOperationException {

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
