package io.pzstorm.storm.core;

import com.google.common.collect.ImmutableSet;
import io.pzstorm.storm.mod.ModJar;
import io.pzstorm.storm.mod.ModMetadata;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class is responsible for registering and loading mod components:
 * <ul>
 * <li>Catalog mod jars by mapping them to mod directory name.</li>
 * <li>Catalog mod metadata by mapping them to mod directory name.</li>
 * <li>Catalog mod classes by mapping them to mod directory name.</li>
 * <li>Load mod classes with {@link StormClassLoader}.</li>
 * </ul>
 */
class StormModLoader {
	 * This catalog stores {@link ModJar} instances mapped to directory names.
	 *
	 * @see #catalogModJars()
	 */
	private static final Map<String, ImmutableSet<ModJar>> JAR_CATALOG = new HashMap<>();

	/**
	 * Find and catalog {@code jar} files found in {@code $HOME/Zomboid/mods/} subdirectories.
	 * The found jars will be mapped to the name of directory in which they were discovered.
	 * Note that directories that do not contain a {@code mod.info} file will be excluded
	 * even if they contain {@code jar} files.
	 *
	 * @throws IOException if unable to create {@code Zomboid/mods} directory structure,
	 * 		an I/O error occurred while walking file tree or constructing {@link ModJar} instance.
	 */
	static void catalogModJars() throws IOException {

		File zomboidLocalDir = getUserHomePath().resolve("Zomboid").toFile();

		if (!zomboidLocalDir.exists() && !zomboidLocalDir.mkdir()) {
			throw new IOException("Unable to create 'Zomboid' directory in '" + getUserHomePath() + '\'');
		}
		File zomboidModsDir = new File(zomboidLocalDir, "mods");
		if (!zomboidModsDir.exists() && !zomboidModsDir.mkdir()) {
			throw new IOException("Unable to create 'mods' directory in '" + zomboidModsDir.toPath() + '\'');
		}
		// clear map before entering new data
		JAR_CATALOG.clear();

		for (Path modDir : listModDirectories(zomboidModsDir))
		{
			Set<ModJar> modJars = new HashSet<>();
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
				}
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
	 * Returns {@code Path} that denotes {@code user.home} system property.
	 */
	private static Path getUserHomePath() {
		return Paths.get(System.getProperty("user.home"));
	}
}
