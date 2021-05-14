package io.pzstorm.storm.core;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.io.CharSink;
import com.google.common.io.MoreFiles;
import io.pzstorm.storm.IntegrationTest;
import io.pzstorm.storm.mod.ModJar;
import io.pzstorm.storm.mod.ModMetadata;
import io.pzstorm.storm.mod.ModVersion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

class StormModLoaderIntegrationTest implements IntegrationTest {

	private static final File TEMP_DIR = IntegrationTest.getTemporaryBuildDir(StormModLoaderIntegrationTest.class);
	private static final File ZOMBOID_MODS_DIR = new File(TEMP_DIR, "Zomboid/mods");

	@BeforeAll
	static void prepareStormModLoaderTest() throws IOException {

		if (TEMP_DIR.exists())
		{
			MoreFiles.deleteDirectoryContents(TEMP_DIR.toPath());
			Assertions.assertEquals(0, Objects.requireNonNull(TEMP_DIR.listFiles()).length);
		}
		else Assertions.assertTrue(ZOMBOID_MODS_DIR.mkdirs());
		System.setProperty("user.home", TEMP_DIR.getPath());
	}

	@Test
	void shouldCatalogAllModJarsFoundInModsDir() throws Throwable {
		testCatalogingModJarsInModsDir(true);
	}

	@Test
	void shouldExcludeModJarsFromCatalogWhenMissingModMetadata() throws Throwable {
		testCatalogingModJarsInModsDir(false);
	}

	@Test
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
			Assertions.assertTrue(modDir.mkdirs());

			writeToModMetadataFile(modDir, ImmutableList.of(
					"name=" + modDirName, "modversion=" + entry.getValue().version));
		}
		testCatalogingModJarsInModsDir(false);
		StormModLoader.loadModMetadata();

		for (Map.Entry<String, ModMetadata> entry : expectedModEntries.entrySet())
		{
			ModMetadata expectedModMetadata = entry.getValue();
			ModMetadata actualModMetadata = StormModLoader.getMetadataCatalogEntry(entry.getKey());
			Assertions.assertEquals(expectedModMetadata, actualModMetadata);
		}
	}

	@Test
	void shouldNotLoadModMetadataWhenModInfoFileMissing() throws Throwable {

		Map<String, ModMetadata> expectedModEntries = ImmutableMap.of(
				"A", new ModMetadata("A", new ModVersion("1.1.0")),
				"B", new ModMetadata("B", new ModVersion("1.1.0")),
				"C", new ModMetadata("C", new ModVersion("1.1.0"))
		);
		testCatalogingModJarsInModsDir(false);
		StormModLoader.loadModMetadata();

		for (Map.Entry<String, ModMetadata> entry : expectedModEntries.entrySet()) {
			Assertions.assertNull(StormModLoader.getMetadataCatalogEntry(entry.getKey()));
		}
	}

	@Test
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
			Assertions.assertTrue(modDir.mkdirs());

			ModMetadata modMetadata = entry.getValue();
			writeToModMetadataFile(modDir, ImmutableList.of(
					modMetadata.name != null ? "name=" + modMetadata.name : "",
					modMetadata.version != null ? "modversion=" + modMetadata.version : ""));
		}
		testCatalogingModJarsInModsDir(false);
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

	@Test
	void shouldLoadAllModClasses() throws Throwable {

		String[] expectedLoadedClasses = new String[] {
				"com.sample.mod.ModA", "com.sample.mod.ModUtils",
				"com.sample.mod.ModB", "com.sample.mod.ModC"
		};
		for (String clazz : expectedLoadedClasses) {
			Assertions.assertFalse(StormBootstrap.CLASS_LOADER.isClassLoaded(clazz));
		}
		testCatalogingModJarsInModsDir(true);

		StormBootstrap.CLASS_LOADER.updateModResourceLoader();
		StormModLoader.loadModClasses();

		for (String clazz : expectedLoadedClasses) {
			Assertions.assertTrue(StormBootstrap.CLASS_LOADER.isClassLoaded(clazz));
		}
	}

	private void testCatalogingModJarsInModsDir(boolean createMetadata) throws Throwable {

		ClassLoader CL = getClass().getClassLoader();
		Map<String, String[]> modJarData = ImmutableMap.of(
				"A", new String[] { "modA.jar" },
				"B", new String[] { "modA.jar", "modB.jar" },
				"C", new String[] { "modA.jar", "modB.jar", "modC.jar" }
		);
		Map<String, ImmutableSet<ModJar>> expectedJarRegistry = new HashMap<>();
		for (Map.Entry<String, String[]> entry : modJarData.entrySet())
		{
			String modDirName = entry.getKey();
			File modDir = new File(ZOMBOID_MODS_DIR, modDirName);

			// create temporary mod directory
			if (!modDir.exists()) {
				Assertions.assertTrue(modDir.mkdir());
			}
			// create metadata file in mod directory
			if (createMetadata) {
				writeToModMetadataFile(modDir, ImmutableList.of("name=" + modDirName, "modversion=1.1.0"));
			}
			Set<ModJar> modJars = new HashSet<>();
			for (String modJarName : entry.getValue())
			{
				URL jarResource = Objects.requireNonNull(CL.getResource("./jars/" + modJarName));
				File destination = new File(modDir, modJarName);

				// copy resource mod jars to temporary directory
				if (!destination.exists())
				{
					Files.copy(Paths.get(jarResource.toURI()), destination.toPath());
					Assertions.assertTrue(destination.exists());
				}
				modJars.add(new ModJar(destination));
			}
			expectedJarRegistry.put(entry.getKey(), ImmutableSet.copyOf(modJars));
		}
		StormModLoader.catalogModJars();
		if (createMetadata)
		{
			for (Map.Entry<String, ImmutableSet<ModJar>> entry : expectedJarRegistry.entrySet()) {
				Assertions.assertEquals(StormModLoader.getJarCatalogEntry(entry.getKey()), entry.getValue());
			}
		}
	}

	private void writeToModMetadataFile(File modDir, List<String> lines) throws IOException {

		File metadataFile = new File(modDir, "mod.info");
		if (!metadataFile.exists()) {
			Assertions.assertTrue(metadataFile.createNewFile());
		}
		CharSink sink = com.google.common.io.Files.asCharSink(metadataFile, StandardCharsets.UTF_8);
		try {
			sink.writeLines(lines, "\n");
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
