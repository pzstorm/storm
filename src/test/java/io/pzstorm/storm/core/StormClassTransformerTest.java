package io.pzstorm.storm.core;

import java.lang.reflect.Constructor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.pzstorm.storm.UnitTest;

class StormClassTransformerTest implements UnitTest {

	@Test
	void shouldChangeStackConstantInInstructionList() throws ReflectiveOperationException {

		String className = "zombie.ZombieHello";

		// create and register transformer
		Class<?> transformerClass = Class.forName(
				"io.pzstorm.storm.core.ZombieHelloTransformer",
				true, StormBootstrap.CLASS_LOADER
		);
		Constructor<?> constructor = transformerClass.getConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();

		Class<?> zombieHello = StormBootstrap.CLASS_LOADER.loadClass(className, true);
		String hello = (String) zombieHello.getDeclaredMethod("getHello").invoke(null);
		Assertions.assertEquals("Zombie says: you die today!", hello);

		zombieHello.getDeclaredMethod("sayHello").invoke(null);
	}
}
