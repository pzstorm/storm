package io.pzstorm.storm.core;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.io.CharSink;
import io.pzstorm.storm.UnitTest;
import io.pzstorm.storm.mod.ModJar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

class StormModLoaderTest implements UnitTest {

	@SuppressWarnings({ "WeakerAccess", "ConstantConditions" })
	@TempDir File tempDir = null;
	private File zomboidModsDir;

	@BeforeEach
	void prepareStormModLoaderTest() {

		zomboidModsDir = new File(tempDir, "Zomboid/mods");
		Assertions.assertTrue(zomboidModsDir.mkdirs());

		System.setProperty("user.home", tempDir.getPath());
	}

	@Test
	void shouldCatalogAllModJarsFoundInModsDir() throws Throwable {
		testCatalogingModJarsInModsDir(true);
	}

	@Test
	void shouldExcludeModJarsFromCatalogWhenMissingModMetadata() throws Throwable {
		testCatalogingModJarsInModsDir(false);
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
			File modDir = new File(zomboidModsDir, modDirName);

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
				Files.copy(Paths.get(jarResource.toURI()), destination.toPath());
				Assertions.assertTrue(destination.exists());

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
