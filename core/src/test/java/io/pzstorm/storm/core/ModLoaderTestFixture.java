package io.pzstorm.storm.core;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.CharSink;
import com.google.common.io.MoreFiles;
import com.google.common.io.RecursiveDeleteOption;

import io.pzstorm.storm.IntegrationTest;

class ModLoaderTestFixture implements IntegrationTest {

	static final ImmutableMap<String, String[]> MOD_JAR_DATA = ImmutableMap.of(
			"A", new String[] { "modA.jar" },
			"B", new String[] { "modB.jar" },
			"C", new String[] { "modB.jar", "modC.jar" }
	);

	static void prepareTestClass(File tempDir) throws IOException {

		if (tempDir.exists())
		{
			// clean temporary directory from last test run
			MoreFiles.deleteDirectoryContents(tempDir.toPath(),RecursiveDeleteOption.ALLOW_INSECURE);
			Assertions.assertEquals(0, Objects.requireNonNull(tempDir.listFiles()).length);
		}
		File zomboidModsDir = new File(tempDir, "Zomboid/mods");
		Assertions.assertTrue(zomboidModsDir.mkdirs());
		System.setProperty("user.home", tempDir.getPath());

		ClassLoader CL = ClassLoader.getSystemClassLoader();
		for (Map.Entry<String, String[]> entry : MOD_JAR_DATA.entrySet())
		{
			String modDirName = entry.getKey();
			File modDir = new File(zomboidModsDir, modDirName);

			// create temporary mod directory
			Assertions.assertTrue(modDir.mkdir());

			for (String modJarName : entry.getValue())
			{
				URI jarResource;
				try {
					jarResource = Objects.requireNonNull(CL.getResource("./jars/" + modJarName)).toURI();
				}
				catch (URISyntaxException e) {
					throw new RuntimeException(e);
				}
				File destination = new File(modDir, modJarName);

				// copy resource mod jars to temporary directory
				if (!destination.exists())
				{
					Files.copy(Paths.get(jarResource), destination.toPath());
					Assertions.assertTrue(destination.exists());
				}
			}
		}
	}

	static void createAndWriteMetadataFiles(File tempDir) throws IOException {

		for (Map.Entry<String, String[]> entry : MOD_JAR_DATA.entrySet())
		{
			String modDirName = entry.getKey();
			File zomboidModsDir = new File(tempDir, "Zomboid/mods");

			writeToModMetadataFile(new File(zomboidModsDir, modDirName),
					ImmutableList.of("name=" + modDirName, "modversion=1.1.0"));
		}
	}

	static void writeToModMetadataFile(File modDir, List<String> lines) throws IOException {

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
