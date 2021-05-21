package io.pzstorm.storm.core;

import java.util.*;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;
import org.jetbrains.annotations.UnmodifiableView;

import com.google.common.collect.ImmutableSet;

import io.pzstorm.storm.logging.StormLogger;
import io.pzstorm.storm.mod.ModMetadata;
import io.pzstorm.storm.mod.ZomboidMod;

/**
 * This class is responsible for creating and registering {@link ZomboidMod} instances.
 */
public class StormModRegistry {

	private static final Map<String, ZomboidMod> MOD_REGISTRY = new HashMap<>();

	/**
	 * Create and register {@link ZomboidMod} instances from cataloged classes.
	 * This method searches for classes that implement {@code ZomboidMod} and then instantiates
	 * and registers the implementation classes. Remember that the class catalogue has to be populated
	 * before registering mods otherwise no mod instances will be created. Also note that only the first
	 * implementation class for each catalog entry will be registered, the rest will be ignored.
	 *
	 * @throws ReflectiveOperationException if an exception was thrown while
	 * 		instantiating {@code ZomboidMod} implementation class.
	 */
	public static void registerMods() throws ReflectiveOperationException {

		for (Map.Entry<String, ImmutableSet<Class<?>>> entry : StormModLoader.CLASS_CATALOG.entrySet())
		{
			// find the first class that implements ZomboidMod interface
			Optional<Class<?>> modClass = entry.getValue().stream()
					.filter(ZomboidMod.class::isAssignableFrom).findFirst();

			if (!modClass.isPresent())
			{
				ModMetadata meta = StormModLoader.METADATA_CATALOG.get(entry.getKey());
				String format = "Unable to find ZomboidMod class for mod '%s'";

				StormLogger.warn(String.format(format, meta != null ? meta.name : "unknown"));
			}
			else MOD_REGISTRY.put(entry.getKey(), (ZomboidMod) modClass.get().getDeclaredConstructor().newInstance());
		}
	}

	/**
	 * Retrieve a {@code Set} of registered mods. Note that the returned
	 * {@code Set} is <b>unmodifiable</b> and modifying it in any way
	 * will result in an {@code UnsupportedOperationException}.
	 */
	public static @UnmodifiableView Set<ZomboidMod> getRegisteredMods() {
		return Collections.unmodifiableSet(new HashSet<>(MOD_REGISTRY.values()));
	}

	@TestOnly
	static @Nullable ZomboidMod getRegisteredMod(String name) {
		return MOD_REGISTRY.get(name);
	}
}
