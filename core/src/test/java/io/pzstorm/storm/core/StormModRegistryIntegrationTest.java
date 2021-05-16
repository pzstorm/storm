package io.pzstorm.storm.core;

import io.pzstorm.storm.IntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URLClassLoader;

class StormModRegistryIntegrationTest extends ModLoaderTestFixture {

	private static final File TEMP_DIR = IntegrationTest.getTemporaryBuildDir(StormModRegistryIntegrationTest.class);

	private static final Class<?> STORM_MOD_LOADER_CLASS;
	private static final Class<?> STORM_MOD_REGISTRY_CLASS;

	private static final Constructor<?> STORM_MOD_LOADER_CONSTRUCTOR;

	private static final Method GET_JAR_RESOURCE_PATHS;
	private static final Method CATALOG_MOD_JARS;
	private static final Method LOAD_MOD_METADATA;
	private static final Method LOAD_MOD_CLASSES;

	private static final Method GET_REGISTERED_MOD;
	private static final Method REGISTER_MODS;

	static
	{
		try {
			STORM_MOD_LOADER_CLASS = Class.forName(
					"io.pzstorm.storm.core.StormModLoader", true, StormBootstrap.CLASS_LOADER
			);
			STORM_MOD_REGISTRY_CLASS = Class.forName(
					"io.pzstorm.storm.core.StormModRegistry", true, StormBootstrap.CLASS_LOADER
			);
			STORM_MOD_LOADER_CONSTRUCTOR = STORM_MOD_LOADER_CLASS.getDeclaredConstructor();
			STORM_MOD_LOADER_CONSTRUCTOR.setAccessible(true);

			GET_JAR_RESOURCE_PATHS = STORM_MOD_LOADER_CLASS.getDeclaredMethod("getJarResourcePaths");
			GET_JAR_RESOURCE_PATHS.setAccessible(true);

			CATALOG_MOD_JARS = STORM_MOD_LOADER_CLASS.getDeclaredMethod("catalogModJars");
			CATALOG_MOD_JARS.setAccessible(true);

			LOAD_MOD_METADATA = STORM_MOD_LOADER_CLASS.getDeclaredMethod("loadModMetadata");
			LOAD_MOD_METADATA.setAccessible(true);

			LOAD_MOD_CLASSES = STORM_MOD_LOADER_CLASS.getDeclaredMethod("loadModClasses");
			LOAD_MOD_CLASSES.setAccessible(true);

			GET_REGISTERED_MOD = STORM_MOD_REGISTRY_CLASS.getDeclaredMethod("getRegisteredMod", String.class);
			GET_REGISTERED_MOD.setAccessible(true);

			REGISTER_MODS = STORM_MOD_REGISTRY_CLASS.getDeclaredMethod("registerMods");
			REGISTER_MODS.setAccessible(true);
		}
		catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	@BeforeAll
	static void prepareStormModLoaderTest() throws IOException {
		prepareTestClass(TEMP_DIR);
	}

	@Test
	void shouldRegisterModsThatHaveZomboidModClass() throws Throwable {

		createAndWriteMetadataFiles(TEMP_DIR);

		CATALOG_MOD_JARS.invoke(null);
		LOAD_MOD_METADATA.invoke(null);

		// assert that mod is not registered
		Assertions.assertNull(GET_REGISTERED_MOD.invoke(null, "A"));

		Constructor<?> constructor = STORM_MOD_LOADER_CLASS.getDeclaredConstructor();
		constructor.setAccessible(true);

		StormBootstrap.CLASS_LOADER.setModResourceLoader(
				(URLClassLoader) STORM_MOD_LOADER_CONSTRUCTOR.newInstance()
		);
		LOAD_MOD_CLASSES.invoke(null);
		REGISTER_MODS.invoke(null);

		Object modA = GET_REGISTERED_MOD.invoke(null, "A");
		Assertions.assertNotNull(modA);

		// assert that no other mods were loaded
		Assertions.assertNull(GET_REGISTERED_MOD.invoke(null, "B"));
		Assertions.assertNull(GET_REGISTERED_MOD.invoke(null, "C"));

		Assertions.assertEquals("com.sample.mod.ModA", modA.getClass().getName());
	}
}
