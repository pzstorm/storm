package io.pzstorm.storm;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;

import com.google.common.collect.ImmutableMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StormClassLoaderTest extends StormClassLoader implements UnitTest {

	private static final ClassLoader CL = StormClassLoaderTest.class.getClassLoader();
	private static final URL CLASSES_RESOURCE_DIR = CL.getResource("./classes/");
	private static final URL DELEGATE_RESOURCE_DIR = CL.getResource("./delegate/");

	StormClassLoaderTest() {
		super(new URL[] { CLASSES_RESOURCE_DIR, DELEGATE_RESOURCE_DIR });
	}

	@Test
	void shouldMatchRawClassByteArrayToClassFile() throws URISyntaxException, IOException {

		File resourceDir = new File(Objects.requireNonNull(CLASSES_RESOURCE_DIR).toURI());
		File expectedClass = new File(resourceDir, "io/pzstorm/storm/SampleClass.class");

		String className = "io.pzstorm.storm.SampleClass";

		byte[] expectedBytes = Files.readAllBytes(expectedClass.toPath());
		byte[] actualBytes = getRawClassByteArray(className);

		for (int i = 0; i < expectedBytes.length; i++) {
			Assertions.assertEquals(expectedBytes[i], actualBytes[i]);
		}
	}

	@Test
	void shouldDelegateToResourceClassLoaderWhenResolvingResource() {

		Assertions.assertNotNull(getResourceAsStream("delegate.resource"));
		Assertions.assertNotNull(findResource("delegate.resource"));
		Assertions.assertNotNull(getResource("delegate.resource"));
	}

	@Test
	void whenIncorrectPackageNameShouldNotDefinePackage() {

		String[] incorrectPackageNames = new String[]{
				"package", ".package", ".", ""
		};
		for (String incorrectPackageName : incorrectPackageNames) {
			Assertions.assertNull(definePackageForName(incorrectPackageName));
		}
	}

	@Test
	void shouldDefinePackageWhenPackageNameIsValid() {

		Map<String, String> validPackageNames = ImmutableMap.of(
				"valid.package", "valid",
				"another.valid.package", "another.valid",
				"yet.another.valid.package", "yet.another.valid"
		);
		for (Map.Entry<String, String> entry : validPackageNames.entrySet())
		{
			Package resultPackage = definePackageForName(entry.getKey());
			Assertions.assertEquals(entry.getValue(), Objects.requireNonNull(resultPackage).getName());
		}
	}

	@Test
	void shouldProperlyRecognizeWhitelistedClassNames() throws ReflectiveOperationException {

		for (String className : getWhitelistedClasses()) {
			Assertions.assertTrue(isWhitelistedClass(className));
		}
		Random rand = new Random();
		for (int i1 = 10; i1 > 0; i1--)
		{
			StringBuilder sb = new StringBuilder();
			sb.append(getRandomString());

			// random number of package directories
			for (int i2 = rand.nextInt(5) + 1; i2 > 0; i2--) {
				sb.append(".").append(getRandomString());
			}
			Assertions.assertFalse(isWhitelistedClass(sb.toString()));
		}
	}

	private static String getRandomString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	@SuppressWarnings("unchecked")
	private static Set<String> getWhitelistedClasses() throws ReflectiveOperationException {

		Field field = StormClassLoader.class.getDeclaredField("CLASS_WHITELIST");
		field.setAccessible(true);

		return (Set<String>) field.get(null);
	}
}
