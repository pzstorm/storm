package io.pzstorm.storm.core;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * This class defines, initializes and stores {@link StormClassTransformer} instances.
 * <ul>
 * <li>Check if transformer is registered by calling {@link #isRegistered(String)}.</li>
 * <li>Retrieve a mapped instance of registered transformer by calling {@link #getRegistered(String)}</li>
 * </ul>
 */
public class StormClassTransformers {

	/**
	 * Internal registry of created transformers. This map is checked for entries
	 * by {@link StormClassLoader} when loading classes and invokes the transformation
	 * chain of methods to transform the class before defining it via JVM.
	 */
	private static final Map<String, StormClassTransformer> TRANSFORMERS = new HashMap<>();


	/**
	 * Returns {@code true} if transformer with given name is registered.
	 */
	static boolean isRegistered(String name) {
		return TRANSFORMERS.containsKey(name);
	}

	/**
	 * Returns registered instance of {@link StormClassTransformer} that matches the given name.
	 *
	 * @return {@code StormClassTransformer} or {@code null} if no registered instance found.
	 */
	@Contract(pure = true)
	public static @Nullable StormClassTransformer getRegistered(String className) {
		return TRANSFORMERS.getOrDefault(className, null);
	}
}
