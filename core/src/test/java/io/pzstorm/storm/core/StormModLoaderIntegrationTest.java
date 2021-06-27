package io.pzstorm.storm.core;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import io.pzstorm.storm.IntegrationTest;
import io.pzstorm.storm.mod.ModJar;
import io.pzstorm.storm.mod.ModMetadata;
import io.pzstorm.storm.mod.ModVersion;

/**
 * Run test methods in a certain order to ensure the tests pass on CI.
 * This is only needed when running on Windows platform because resources like
 * metadata and jar files cannot be removed once they have been loaded by JVM.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StormModLoaderIntegrationTest extends ModLoaderTestFixture {

	/* do not use TempDir annotation to create temporary directory
	 * because Windows cannot delete these temporary directories due
	 * to jar files in those directories being in use by JVM
	 */
	private static final File TEMP_DIR = IntegrationTest.getTemporaryBuildDir(StormModLoaderIntegrationTest.class);
	private static final File ZOMBOID_MODS_DIR = new File(TEMP_DIR, "Zomboid/mods");

	@BeforeAll
	static void prepareStormModLoaderTest() throws IOException {
		prepareTestClass(TEMP_DIR);
	}

	/**
	 * Run this method before others because it does not require metadata files present.
	 */
	@Test
	@Order(1)
	void shouldExcludeModJarsFromCatalogWhenMissingModMetadata() throws Throwable {

		StormModLoader.catalogModJars();
		for (Map.Entry<String, String[]> entry : MOD_JAR_DATA.entrySet()) {
			Assertions.assertNull(StormModLoader.getJarCatalogEntry(entry.getKey()));
		}
	}

	/**
	 * Run this method before others because it does not require metadata files present.
	 */
	@Test
	@Order(2)
	void shouldNotLoadModMetadataWhenModInfoFileMissing() throws Throwable {

		Map<String, ModMetadata> expectedModEntries = ImmutableMap.of(
				"A", new ModMetadata("A", new ModVersion("1.1.0")),
				"B", new ModMetadata("B", new ModVersion("1.1.0")),
				"C", new ModMetadata("C", new ModVersion("1.1.0"))
		);
		StormModLoader.loadModMetadata();

		for (Map.Entry<String, ModMetadata> entry : expectedModEntries.entrySet()) {
			Assertions.assertNull(StormModLoader.getMetadataCatalogEntry(entry.getKey()));
		}
	}

	@Test
	@Order(3)
	void shouldCatalogAllModJarsFoundInModsDir() throws Throwable {

		// write metadata files
		createAndWriteMetadataFiles(TEMP_DIR);

		Map<String, ImmutableSet<ModJar>> expectedJarRegistry = new HashMap<>();
		for (Map.Entry<String, String[]> entry : MOD_JAR_DATA.entrySet())
		{
			File modDir = new File(ZOMBOID_MODS_DIR, entry.getKey());

			Set<ModJar> modJars = new HashSet<>();
			for (String modJarName : entry.getValue()) {
				modJars.add(new ModJar(new File(modDir, modJarName)));
			}
			expectedJarRegistry.put(entry.getKey(), ImmutableSet.copyOf(modJars));
		}
		StormModLoader.catalogModJars();
		for (Map.Entry<String, ImmutableSet<ModJar>> entry : expectedJarRegistry.entrySet()) {
			Assertions.assertEquals(StormModLoader.getJarCatalogEntry(entry.getKey()), entry.getValue());
		}
	}

	@Test
	@Order(4)
	void shouldIncludeModDirectoryPathsInResourcePaths() {

		StormModLoader modLoader = new StormModLoader();
		HashSet<Path> classLoaderURLs = new HashSet<>();
		for (URL resourcePath : modLoader.getURLs()) {
			classLoaderURLs.add(Paths.get(resourcePath.getPath()).toAbsolutePath());
		}
		for (String modDirName : new String[] { "A", "B", "C" })
		{
			File modDir = new File(ZOMBOID_MODS_DIR, modDirName).getAbsoluteFile();
			Assertions.assertTrue(classLoaderURLs.contains(modDir.toPath()));
		}
	}

	@Test
	@Order(5)
	void shouldLoadAllModClasses() throws IOException {

		String[] expectedLoadedClasses = new String[] {
				"com.sample.mod.ModA", "com.sample.mod.ModB", "com.sample.mod.ModC"
		};
		StormModLoader.catalogModJars();
		StormBootstrap.CLASS_LOADER.setModResourceLoader(new StormModLoader());
		StormModLoader.loadModClasses();

		for (String clazz : expectedLoadedClasses) {
			Assertions.assertTrue(StormBootstrap.CLASS_LOADER.isClassLoaded(clazz));
		}
	}

	@Test
	@Order(6)
	void shouldCorrectlyLoadAllModMetadata() throws Throwable {

		Map<String, ModMetadata> expectedModEntries = ImmutableMap.of(
				"A", new ModMetadata("A", new ModVersion("1.7.0")),
				"B", new ModMetadata("B", new ModVersion("0.3.0")),
				"C", new ModMetadata("C", new ModVersion("2.0.0"))
		);
		for (Map.Entry<String, ModMetadata> entry : expectedModEntries.entrySet())
		{
			String modDirName = entry.getKey();
			File modDir = new File(ZOMBOID_MODS_DIR, modDirName);
			if (!modDir.exists()) {
				Assertions.assertTrue(modDir.mkdirs());
			}
			writeToModMetadataFile(modDir, ImmutableList.of(
					"name=" + modDirName, "modversion=" + entry.getValue().version));
		}
		StormModLoader.catalogModJars();
		StormModLoader.loadModMetadata();

		for (Map.Entry<String, ModMetadata> entry : expectedModEntries.entrySet())
		{
			ModMetadata expectedModMetadata = entry.getValue();
			ModMetadata actualModMetadata = StormModLoader.getMetadataCatalogEntry(entry.getKey());
			Assertions.assertEquals(expectedModMetadata, actualModMetadata);
		}
	}

	@Test
	@Order(7)
	void shouldNotLoadModMetadataWhenMissingProperties() throws Throwable {

		Map<String, ModMetadata> modEntries = ImmutableMap.of(
				"A", new ModMetadata("A", new ModVersion("1.0.0")),
				"B", new ModMetadata(null, new ModVersion("2.4.0")),
				"C", new ModMetadata("", new ModVersion("0.3.0")),
				"D", new ModMetadata("D", null),
				"E", new ModMetadata("E", new ModVersion(""))
		);
		for (Map.Entry<String, ModMetadata> entry : modEntries.entrySet())
		{
			String modDirName = entry.getKey();
			File modDir = new File(ZOMBOID_MODS_DIR, modDirName);
			if (!modDir.exists()) {
				Assertions.assertTrue(modDir.mkdirs());
			}
			ModMetadata modMetadata = entry.getValue();
			writeToModMetadataFile(modDir, ImmutableList.of(
					modMetadata.name != null ? "name=" + modMetadata.name : "",
					modMetadata.version != null ? "modversion=" + modMetadata.version : ""));
		}
		StormModLoader.catalogModJars();
		StormModLoader.loadModMetadata();

		// both name and version properties present
		Assertions.assertNotNull(StormModLoader.getMetadataCatalogEntry("A"));

		// missing name property
		Assertions.assertNull(StormModLoader.getMetadataCatalogEntry("B"));

		// name property is empty string
		Assertions.assertNull(StormModLoader.getMetadataCatalogEntry("C"));

		// missing version property
		Assertions.assertNull(StormModLoader.getMetadataCatalogEntry("D"));

		// version property is empty string
		ModMetadata modE = StormModLoader.getMetadataCatalogEntry("E");
		Assertions.assertNotNull(modE);

		// assert using default version
		Assertions.assertEquals(new ModVersion("0.1.0"), modE.version);
	}
}
