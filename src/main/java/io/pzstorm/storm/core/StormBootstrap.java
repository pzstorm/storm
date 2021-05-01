package io.pzstorm.storm.core;

import java.lang.reflect.Method;

/**
 * This class bootstraps everything needed to launch the game with static initialization.
 * It should be loaded from {@link StormLauncher} before Storm attempts to launch the game.
 */
class StormBootstrap {

	/**
	 * {@code ClassLoader} used to transform and load all needed classes. Because class loaders
	 * maintain their own set of class instances and native libraries you should always use
	 * this loader to load classes that access or modify transformed class fields or methods.
	 */
	static final StormClassLoader CLASS_LOADER = new StormClassLoader();

	/**
	 * Loaded and initialized {@link StormClassTransformer} {@code Class}. To transform specific
	 * classes during load time (<i>on-fly</i>) {@link StormClassLoader} has to read and invoke
	 * registered transformers. Due to how class loading works in Java references to classes within
	 * {@code ClassLoader} do not get loaded by that specific {@code ClassLoader} but get delegate
	 * to {@code AppClassLoader}. For this reason we have to use bootstrapping and reflection to
	 * access transformers from {@code StormClassLoader}.
	 */
	static final Class<?> TRANSFORMER_CLASS;

	/**
	 * Represents {@link StormClassTransformer#getRegistered(String)} method.
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

	static
	{
		try {
			String transformerClass = "io.pzstorm.storm.core.StormClassTransformer";
			TRANSFORMER_CLASS = Class.forName(transformerClass, true, CLASS_LOADER);

			TRANSFORMER_GETTER = TRANSFORMER_CLASS.getDeclaredMethod("getRegistered", String.class);
			TRANSFORMER_INVOKER = TRANSFORMER_CLASS.getDeclaredMethod("transform", byte[].class);
		}
		catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns registered instance of {@link StormClassTransformer} that matches the given name.
	 *
	 * @throws ReflectiveOperationException if an error occurred while invoking method.
	 * @see StormClassTransformer#getRegistered(String)
	 */
	static Object getRegisteredTransformer(String name) throws ReflectiveOperationException {
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
}
