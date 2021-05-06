package io.pzstorm.storm.core;

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import io.pzstorm.storm.hook.StormHook;

/**
 * This class defines, initializes and stores {@link StormClassTransformer} instances.
 * To retrieve a mapped instance of registered transformer call {@link #getRegistered(String)}.
 */
@SuppressWarnings("WeakerAccess")
public class StormClassTransformers {

	/**
	 * Internal registry of created transformers. This map is checked for entries
	 * by {@link StormClassLoader} when loading classes and invokes the transformation
	 * chain of methods to transform the class before defining it via JVM.
	 */
	private static final Map<String, StormClassTransformer> TRANSFORMERS = new HashMap<>();


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
