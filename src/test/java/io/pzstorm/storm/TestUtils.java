package io.pzstorm.storm;

import io.pzstorm.storm.logging.StormLogger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

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

	/**
	 * Sets the given instance {@link Field} to value designated by method parameter.
	 * This method will work for all modifiers but was primarily intended for
	 * fields with {@code private} and {@code final} modifiers.
	 *
	 * @param field {@code Field} to set the value of.
	 * @param instance the object whose field should be modified.
	 * @param value the new value for the field of obj being modified.
	 */
	public static void setPrivateFinalFieldToValue(Field field, Object instance, Object value) {

		try {
			Field modifiersField = Field.class.getDeclaredField("modifiers");

			modifiersField.setAccessible(true);
			modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

			field.setAccessible(true);
			field.set(instance, value);
		}
		catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}
}
