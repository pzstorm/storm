package io.pzstorm.storm.core;

import java.lang.reflect.Method;

import org.jetbrains.annotations.Nullable;

/**
 * This class bootstraps everything needed to launch the game with static initialization.
 * It should be loaded from {@link StormLauncher} before Storm attempts to launch the game.
 */
@SuppressWarnings("WeakerAccess")
public class StormBootstrap {

	/**
	 * {@code ClassLoader} used to transform and load all needed classes.
	 * This includes both Project Zomboid and mod classes. Because class loaders maintain
	 * their own set of class instances and native libraries this loader should always be
	 * used to load classes that access or modify transformed class fields or methods.
	 */
	public static final StormClassLoader CLASS_LOADER = new StormClassLoader();

	/**
	 * Loaded and initialized {@link StormClassTransformer} {@code Class}. To transform specific
	 * classes during load time (<i>on-fly</i>) {@link StormClassLoader} has to read and invoke
	 * registered transformers. Due to how class loading works in Java references to classes within
	 * {@code ClassLoader} do not get loaded by that specific {@code ClassLoader} but get delegate
	 * to {@code AppClassLoader}. For this reason we have to use bootstrapping and reflection to
	 * access transformers from {@code StormClassLoader}.
	 */
	private static final Class<?> TRANSFORMER_CLASS;

	/**
	 * Loaded and initialized {@link StormClassTransformers} {@code Class}.
	 */
	private static final Class<?> TRANSFORMERS_CLASS;

	/**
	 * Represents {@link StormClassTransformers#getRegistered(String)} method.
	 *
	 * @see #getRegisteredTransformer(String)
	 */
	private static final Method TRANSFORMER_GETTER;

	/**
	 * Represents {@link StormClassTransformer#transform(byte[])} method.
	 *
	 * @see #invokeTransformer(Object, byte[])
	 */
	private static final Method TRANSFORMER_INVOKER;

	/**
	 * Marks the {@code StormBoostrap} as being fully loaded. This variable will be
	 * {@code true} when the static block has finished initializing. Required by classes
	 * that are loaded before {@code StormBoostrap} but still depend on it.
	 */
	private static final boolean hasLoaded;

	static
	{
		try {
			TRANSFORMER_CLASS = Class.forName(
					"io.pzstorm.storm.core.StormClassTransformer", true, CLASS_LOADER
			);
			TRANSFORMERS_CLASS = Class.forName(
					"io.pzstorm.storm.core.StormClassTransformers", true, CLASS_LOADER
			);
			TRANSFORMER_GETTER = TRANSFORMERS_CLASS.getDeclaredMethod("getRegistered", String.class);
			TRANSFORMER_INVOKER = TRANSFORMER_CLASS.getDeclaredMethod("transform", byte[].class);

			// mark StormBootstrap as finished loading
			hasLoaded = true;
		}
		catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns registered instance of {@link StormClassTransformer} that matches the given name.
	 *
	 * @throws ReflectiveOperationException if an error occurred while invoking method.
	 * @see StormClassTransformers#getRegistered(String)
	 */
	static @Nullable Object getRegisteredTransformer(String name) throws ReflectiveOperationException {
		return TRANSFORMER_GETTER.invoke(null, name);
	}

	/**
	 * Calls method chain to transform the given {@code Class} byte array.
	 *
	 * @throws ReflectiveOperationException if an error occurred while invoking method.
	 * @see StormClassTransformer#transform(byte[])
	 */
	@SuppressWarnings("RedundantCast")
	static byte[] invokeTransformer(Object transformer, byte[] rawClass) throws ReflectiveOperationException {
		return (byte[]) TRANSFORMER_INVOKER.invoke(transformer, (Object) rawClass);
	}

	/**
	 * Returns if {@code StormBoostrap} has finished loading.
	 *
	 * @return {@code true} if boostrap has been fully loaded.
	 */
	static boolean hasLoaded() {
		return hasLoaded;
	}
}
