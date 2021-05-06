package io.pzstorm.storm.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

class StormUtilsTest {

	private boolean hasInvokedRestrictedMethod = false;

	@Test
	void shouldResetAccessibilityAfterInvokingRestrictedMethod() throws ReflectiveOperationException {

		Method method = StormUtilsTest.class.getDeclaredMethod("inaccessibleMethod", String.class, Integer.class);

		Assertions.assertFalse(method.isAccessible());
		StormUtils.invokeRestrictedMethod(method, this, "sample", 0);
		Assertions.assertFalse(method.isAccessible());
	}

	@Test
	void shouldInvokeRestrictedMethod() throws ReflectiveOperationException {

		Method method = StormUtilsTest.class.getDeclaredMethod("inaccessibleMethod", String.class, Integer.class);

		StormUtils.invokeRestrictedMethod(method, this, "sample", 0);
		Assertions.assertTrue(hasInvokedRestrictedMethod);
	}

	private void inaccessibleMethod(String arg1, Integer arg2) {
		hasInvokedRestrictedMethod = true;
	}
}
