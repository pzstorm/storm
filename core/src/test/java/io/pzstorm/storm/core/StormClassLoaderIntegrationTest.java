package io.pzstorm.storm.core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import io.pzstorm.storm.IntegrationTest;

@SuppressWarnings("SpellCheckingInspection")
class StormClassLoaderIntegrationTest extends StormClassLoader implements IntegrationTest {

	private static final ClassLoader CL = StormClassLoaderIntegrationTest.class.getClassLoader();
	private static final URL CLASSES_RESOURCE_DIR = Objects.requireNonNull(CL.getResource("./classes/"));
	private static final URL DELEGATE_RESOURCE_DIR = Objects.requireNonNull(CL.getResource("./delegate/"));

	StormClassLoaderIntegrationTest() {
		super(new URL[] { CLASSES_RESOURCE_DIR, DELEGATE_RESOURCE_DIR });
	}

	private static String getRandomString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	@SuppressWarnings("unchecked")
	private static Set<String> getBlacklistedClasses() throws ReflectiveOperationException {

		Field field = StormClassLoader.class.getDeclaredField("CLASS_BLACKLIST");
		field.setAccessible(true);

		return (Set<String>) field.get(null);
	}

	@Test
	void shouldLoadNonBlacklistedClassesWithStormClassLoader() throws ReflectiveOperationException {

		for (String blacklistedClasses : getBlacklistedClasses()) {
			Assertions.assertNull(findLoadedClass(blacklistedClasses));
		}
		ImmutableSet<String> dummyGameClasses = ImmutableSet.of(
				"fmod.javafmod", "jassimp.Jassimp", "javax.vecmath.Point2f",
				"org.lwjgl.Version", "zombie.gameStates.MainScreenState"
		);
		for (String dummyGameClass : dummyGameClasses)
		{
			Class<?> loadedClass = loadClass(dummyGameClass, true);
			Assertions.assertEquals(loadedClass, findLoadedClass(dummyGameClass));
			Assertions.assertEquals(this, loadedClass.getClassLoader());
		}
	}

	@Test
	void shouldDelegateLoadingBlacklistedClassesToParentClassLoader() throws ReflectiveOperationException {

		Method method = ClassLoader.class.getDeclaredMethod("findLoadedClass", String.class);
		method.setAccessible(true);

		ImmutableSet<String> dummyClasses = ImmutableSet.of(
				"java.util.concurrent.CountDownLatch",
				"org.objectweb.asm.ClassWriter",
				"javax.imageio.IIOImage",
				"javax.xml.XMLConstants",
				"org.w3c.dom.Attr"
		);
		for (String dummyClass : dummyClasses)
		{
			ClassLoader classLoader = loadClass(dummyClass, true).getClassLoader();
			Assertions.assertTrue(classLoader == null || !classLoader.equals(this));
		}
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

		String[] incorrectPackageNames = new String[] {
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
	void shouldProperlyRecognizeBlacklistedClassNames() throws ReflectiveOperationException {

		for (String className : getBlacklistedClasses()) {
			Assertions.assertTrue(isBlacklistedClass(className));
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
			Assertions.assertFalse(isBlacklistedClass(sb.toString()));
		}
	}
}
