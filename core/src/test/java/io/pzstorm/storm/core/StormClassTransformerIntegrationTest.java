package io.pzstorm.storm.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.pzstorm.storm.IntegrationTest;

class StormClassTransformerIntegrationTest implements IntegrationTest {

	private static final Class<?> STORM_CLASS_TRANSFORMER, STORM_CLASS_TRANSFORMERS;

	static
	{
		try {
			STORM_CLASS_TRANSFORMER = Class.forName(
					"io.pzstorm.storm.core.StormClassTransformer",
					true, StormBootstrap.CLASS_LOADER
			);
			STORM_CLASS_TRANSFORMERS = Class.forName(
					"io.pzstorm.storm.core.StormClassTransformers",
					true, StormBootstrap.CLASS_LOADER);
		}
		catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private static void createAndRegisterTransformer(String className, String sTransformerClass) throws ReflectiveOperationException {

		Class<?> transformerClass = Class.forName(sTransformerClass, true, StormBootstrap.CLASS_LOADER);
		Constructor<?> constructor = transformerClass.getConstructor();
		constructor.setAccessible(true);

		Object transformer = constructor.newInstance();
		Method registerTransformer = STORM_CLASS_TRANSFORMERS.getDeclaredMethod(
				"registerTransformer", String.class, STORM_CLASS_TRANSFORMER
		);
		registerTransformer.setAccessible(true);
		registerTransformer.invoke(null, className, STORM_CLASS_TRANSFORMER.cast(transformer));
	}

	@Test
	void shouldChangeStackConstantInInstructionList() throws ReflectiveOperationException {

		String className = "zombie.ZombieHello";
		createAndRegisterTransformer(className, "io.pzstorm.storm.core.ZombieHelloTransformer");
		Class<?> zombieHello = StormBootstrap.CLASS_LOADER.loadClass(className, true);
		String hello = (String) zombieHello.getDeclaredMethod("getHello").invoke(null);
		Assertions.assertEquals("Zombie says: you die today!", hello);

		zombieHello.getDeclaredMethod("sayHello").invoke(null);
	}

	@Test
	void shouldInsertInstructionBeforeMatchedLabel() throws ReflectiveOperationException {

		String className = "zombie.ZombieUtils";
		createAndRegisterTransformer(className, "io.pzstorm.storm.core.ZombieUtilsTransformer");

		Class<?> zombieUtils = StormBootstrap.CLASS_LOADER.loadClass(className, true);
		zombieUtils.getDeclaredMethod("setZombieProperties", int.class, boolean.class)
				.invoke(null, 21, true);

		int propertyA = (int) zombieUtils.getDeclaredField("zombiePropertyA").get(null);
		boolean propertyC = (boolean) zombieUtils.getDeclaredField("zombiePropertyC").get(null);

		Assertions.assertEquals(21, propertyA);
		Assertions.assertTrue(propertyC);
	}
}
