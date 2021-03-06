package io.pzstorm.storm.util;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.pzstorm.storm.TestUtils;
import io.pzstorm.storm.UnitTest;

@SuppressWarnings("unused")
class StormUtilsTest implements UnitTest {

	private boolean hasInvokedRestrictedMethod = false;

	@Test
	void shouldGetClassAsCorrectPath() {

		String expected = "io/pzstorm/storm/util/StormUtilsTest";
		Assertions.assertEquals(expected, StormUtils.getClassAsPath(StormUtilsTest.class));
	}

	@Test
	void shouldResetAccessibilityAfterInvokingRestrictedMethod() throws ReflectiveOperationException {

		Method method = StormUtilsTest.class.getDeclaredMethod("inaccessibleMethod", String.class, Integer.class);

		Assertions.assertFalse(method.isAccessible());
		TestUtils.invokeRestrictedMethod(method, this, "sample", 0);
		Assertions.assertFalse(method.isAccessible());
	}

	@Test
	void shouldInvokeRestrictedMethod() throws ReflectiveOperationException {

		Method method = StormUtilsTest.class.getDeclaredMethod("inaccessibleMethod", String.class, Integer.class);

		TestUtils.invokeRestrictedMethod(method, this, "sample", 0);
		Assertions.assertTrue(hasInvokedRestrictedMethod);
	}

	private void inaccessibleMethod(String arg1, Integer arg2) {
		hasInvokedRestrictedMethod = true;
	}
}
