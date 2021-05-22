/*
 * Zomboid Storm - Java modding toolchain for Project Zomboid
 * Copyright (C) 2021 Matthew Cain
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package io.pzstorm.storm.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ObjectArrays;

import io.pzstorm.storm.logging.StormLogger;
import io.pzstorm.storm.mod.ModJar;
import io.pzstorm.storm.mod.ModMetadata;
import io.pzstorm.storm.mod.ModVersion;

/**
 * This class is responsible for loading mod components:
 * <ul>
 * <li>Catalog mod jars by mapping them to mod directory name.</li>
 * <li>Catalog mod metadata by mapping them to mod directory name.</li>
 * <li>Catalog mod classes by mapping them to mod directory name.</li>
 * <li>Load mod classes with {@link StormClassLoader}.</li>
 * </ul>
 */
public class StormModLoader extends URLClassLoader {

	/**
	 * This catalog contains {@link ModMetadata} instances mapped to directory names.
	 */
	static final Map<String, ModMetadata> METADATA_CATALOG = new HashMap<>();
	/**
	 * This catalog stores {@link Class} instances mapped to directory names.
	 */
	static final Map<String, ImmutableSet<Class<?>>> CLASS_CATALOG = new HashMap<>();
	/**
	 * This catalog stores {@link ModJar} instances mapped to directory names.
	 */
	private static final Map<String, ImmutableSet<ModJar>> JAR_CATALOG = new HashMap<>();

	StormModLoader(URL[] urls) {
		super(ObjectArrays.concat(urls, getJarResourcePaths(), URL.class));
	}

	public StormModLoader() {
		super(getJarResourcePaths());
	}

	/**
	 * Returns an array of paths pointing to cataloged jars.
	 * This method will return an empty array if no jars are cataloged.
	 */
	private static URL[] getJarResourcePaths() {

		List<URL> result = new ArrayList<>();
		for (Set<ModJar> modJars : JAR_CATALOG.values()) {
			modJars.forEach(j -> result.add(j.getResourcePath()));
		}
		return result.toArray(new URL[0]);
	}

	/**
	 * Find and catalog {@code jar} files found in {@code $HOME/Zomboid/mods/} subdirectories.
	 * The found jars will be mapped to the name of directory in which they were discovered.
	 * Note that directories that do not contain a {@code mod.info} file will be excluded
	 * even if they contain {@code jar} files.
	 *
	 * @throws IOException if unable to create {@code Zomboid/mods} directory structure,
	 * 		an I/O error occurred while walking file tree or constructing {@link ModJar} instance.
	 */
	public static void catalogModJars() throws IOException {

		StormLogger.info(String.format("%s mod jar catalog", JAR_CATALOG.isEmpty() ? "Building" : "Rebuilding"));
		File zomboidLocalDir = getUserHomePath().resolve("Zomboid").toFile();

		if (!zomboidLocalDir.exists() && !zomboidLocalDir.mkdir()) {
			throw new IOException("Unable to create 'Zomboid' directory in '" + getUserHomePath() + '\'');
		}
		File zomboidModsDir = new File(zomboidLocalDir, "mods");
		StormLogger.debug("Cataloging mod jars in '" + zomboidModsDir.getPath() + '\'');

		if (!zomboidModsDir.exists() && !zomboidModsDir.mkdir()) {
			throw new IOException("Unable to create 'mods' directory in '" + zomboidModsDir.toPath() + '\'');
		}
		// clear map before entering new data
		JAR_CATALOG.clear();

		for (Path modDir : listModDirectories(zomboidModsDir))
		{
			Set<ModJar> modJars = new HashSet<>();
			String modFileName = modDir.getFileName().toString();
			try (Stream<Path> stream = Files.walk(modDir, 1))
			{
				Set<Path> files = stream.filter(Files::isRegularFile).collect(Collectors.toSet());

				// mod directory has to contain mod metadata file
				if (files.contains(modDir.resolve("mod.info")))
				{
					for (Path modJar : listJarsInDirectory(modDir)) {
						modJars.add(new ModJar(modJar.toFile()));
					}
					JAR_CATALOG.put(modDir.toFile().getName(), ImmutableSet.copyOf(modJars));

					StormLogger.debug("Created new jar catalog entry:");
					StormLogger.debug(String.format("Found %d jars in mod directory '%s'", modJars.size(), modFileName));
				}
				else StormLogger.warn("Skipped jar catalog entry for mod directory '" + modFileName + '\'');
			}
		}
	}

	/**
	 * Returns a {@code Set} of paths that denote subdirectories in the given directory {@code File}.
	 * This method will not employ recursion when searching for files. Search depth is one
	 * directory which means that only immediate subdirectories will be included.
	 *
	 * @param zomboidDir directory to search for subdirectories.
	 *
	 * @throws RuntimeException if an I/O error occurs when opening the directory.
	 */
	private static Set<Path> listModDirectories(File zomboidDir) {

		try (Stream<Path> stream = Files.list(zomboidDir.toPath())) {
			return stream.filter(Files::isDirectory).collect(Collectors.toSet());
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns a {@code Set} of paths that denote jar files in given directory. This method will
	 * not employ recursion when searching for files. Search depth is one directory which
	 * means that only immediate jar files will be included.
	 *
	 * @param modDir {@code Path} that points to directory to search for jar files.
	 *
	 * @throws RuntimeException if an I/O error occurs when opening the directory.
	 */
	private static Set<Path> listJarsInDirectory(Path modDir) {

		try (Stream<Path> stream = Files.walk(modDir, 1)) {
			return stream.filter(p -> p.getFileName().toString().endsWith(".jar")).collect(Collectors.toSet());
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Load mod metadata for each entry in jar catalog. Before loading mod metadata it is
	 * important to populate the jar catalog by calling {@link #catalogModJars()} method,
	 * otherwise this method will only clear the metadata catalog. Metadata is read from
	 * {@code mod.info} file located in mod root directory. Directories which do not
	 * contain a metadata file will be excluded from this operation.
	 *
	 * @throws IOException if an error occurred when reading from input stream.
	 */
	public static void loadModMetadata() throws IOException {

		StormLogger.info(String.format("%s mod metadata catalog", JAR_CATALOG.isEmpty() ? "Building" : "Rebuilding"));

		// clear map before entering new data
		METADATA_CATALOG.clear();

		Path zomboidModsDir = getUserHomePath().resolve("Zomboid/mods");
		for (String modEntry : JAR_CATALOG.keySet())
		{
			File modInfoFile = Paths.get(zomboidModsDir.toString(), modEntry, "mod.info").toFile();
			if (!modInfoFile.exists() || modInfoFile.isDirectory())
			{
				String message = "Unable to register mod in directory '%s' missing 'mod.info' file";
				StormLogger.error(message, modEntry);
				continue;
			}
			Properties modInfo = new Properties();
			modInfo.load(new FileInputStream(modInfoFile));
			StormLogger.debug("Found metadata file for entry '" + modEntry + '\'');

			String modName = modInfo.getProperty("name");
			if (Strings.isNullOrEmpty(modName))
			{
				String message = "Unable to register mod in directory '%s' with missing name, check mod.info file";
				StormLogger.error(String.format(message, modEntry));
				continue;
			}
			String modVersion = modInfo.getProperty("modversion");
			if (modVersion == null)
			{
				String message = "Unable to register mod '%s' with missing version, check mod.info file";
				StormLogger.error(String.format(message, modName));
				continue;
			}
			else if (modVersion.isEmpty())
			{
				String message = "Mod '%s' has empty version property, using 0.1.0 version instead";
				StormLogger.warn(message, modName);
				modVersion = "0.1.0";
			}
			ModMetadata metadata = new ModMetadata(modName, new ModVersion(modVersion));
			METADATA_CATALOG.put(modName, metadata);

			StormLogger.debug("Created new metadata catalog entry: " + metadata);
		}
	}

	/**
	 * Load classes from cataloged {@link ModJar} instances with {@link StormClassLoader}.
	 * Once loaded the classes will also be cataloged by mapping them to directory name.
	 * Before loading classes it is important to populate the jar catalog with
	 * {@link #catalogModJars()} method, otherwise this method will only clear the class catalog.
	 */
	public static void loadModClasses() {
		StormLogger.info(String.format("%s mod class catalog", JAR_CATALOG.isEmpty() ? "Building" : "Rebuilding"));

		// clear map before entering new data
		CLASS_CATALOG.clear();

		for (Map.Entry<String, ImmutableSet<ModJar>> entry : JAR_CATALOG.entrySet())
		{
			Set<Class<?>> modClasses = new HashSet<>();
			for (ModJar modJar : entry.getValue())
			{
				Enumeration<JarEntry> jarEntries = modJar.entries();
				while (jarEntries.hasMoreElements())
				{
					JarEntry jarEntry = jarEntries.nextElement();
					if (jarEntry.isDirectory() || !jarEntry.getName().endsWith(".class")) {
						continue;
					}
					String entryName = jarEntry.getName();
					String className = entryName.substring(0, entryName.length() - 6);
					try {
						modClasses.add(StormBootstrap.CLASS_LOADER.loadClass(
								className.replace('/', '.'), true));
					}
					catch (ClassNotFoundException e) {
						throw new RuntimeException(e);
					}
				}
			}
			CLASS_CATALOG.put(entry.getKey(), ImmutableSet.copyOf(modClasses));

			StormLogger.debug("Created new metadata catalog entry:");
			StormLogger.debug(String.format("Found %d classes in mod directory '%s'", modClasses.size(), entry.getKey()));
		}
	}

	/**
	 * Returns {@code Path} that denotes {@code user.home} system property.
	 */
	private static Path getUserHomePath() {
		return Paths.get(System.getProperty("user.home"));
	}

	@TestOnly
	static @Nullable ModMetadata getMetadataCatalogEntry(String name) {
		return METADATA_CATALOG.get(name);
	}

	@TestOnly
	static @Nullable ImmutableSet<ModJar> getJarCatalogEntry(String name) {
		return JAR_CATALOG.get(name);
	}
}
