package io.pzstorm.storm.core;

import java.util.HashMap;
import java.util.Map;

import io.pzstorm.storm.hook.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import io.pzstorm.storm.patch.DebugLogPatch;
import io.pzstorm.storm.patch.DebugLogStreamPatch;
import io.pzstorm.storm.patch.ZomboidPatch;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * This class defines, initializes and stores {@link StormClassTransformer} instances.
 * To retrieve a mapped instance of registered transformer call {@link #getRegistered(String)}.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class StormClassTransformers {

	/**
	 * Internal registry of created transformers. This map is checked for entries
	 * by {@link StormClassLoader} when loading classes and invokes the transformation
	 * chain of methods to transform the class before defining it via JVM.
	 */
	private static final Map<String, StormClassTransformer> TRANSFORMERS = new HashMap<>();

	static
	{
		/////////////////////
		// REGISTER HOOKS //
		///////////////////

		registerTransformer("zombie.gameStates.MainScreenState", new OnMainScreenRenderHook());
		registerTransformer("zombie.ui.UIElement", new OnUIElementPreRenderHook());

		///////////////////////
		// REGISTER PATCHES //
		/////////////////////

		registerTransformer("zombie.debug.DebugLog", new DebugLogPatch());
		registerTransformer("zombie.debug.DebugLogStream", new DebugLogStreamPatch());
	}

	/**
	 * Register designated {@link StormClassTransformer} with given name.
	 *
	 * @param className name of the target class to transform.
	 * @param transformer {@code StormClassTransformer} to register.
	 */
	private static void registerTransformer(String className, StormClassTransformer transformer) {
		TRANSFORMERS.put(className, transformer);
	}

	/**
	 * Create and register a new {@link StormClassTransformer} with given name
	 * that applies a {@link ZomboidPatch} designated by method parameter.
	 *
	 * @param className name of the target class to transform.
	 * @param patch {@code ZomboidPatch} to apply with transformation.
	 */
	private static void registerTransformer(String className, ZomboidPatch patch) {

		TRANSFORMERS.put(className, new StormClassTransformer(className) {

			@Override
			StormClassTransformer transform() {

				patch.applyPatch(this);
				return this;
			}
		});
	}

	/**
	 * Create and register a new {@link StormClassTransformer} with given name that
	 * installs a {@link StormHook} designated by method parameter. Additionally this
	 * method also defines the maximum stack size of methods in visited class.
	 *
	 * @param className name of the target class to transform.
	 * @param hook {@link StormHook} to install with transformation.
	 * @param maxStacks maximum stack size mapped to method data.
	 */
	private static void registerTransformer(String className, StormHook hook, Map<MethodData, Integer> maxStacks) {

		ClassNode visitor = new ClassNode(Opcodes.ASM9) {

			@Override
			public MethodVisitor visitMethod(int access, String name, String descriptor,
											 String signature, String[] exceptions) {

				for (Map.Entry<MethodData, Integer> entry : maxStacks.entrySet())
				{
					MethodData data = entry.getKey();
					if (name.equals(data.name) && descriptor.equals(data.descriptor))
					{
						MethodNode method = new MethodNode(Opcodes.ASM9, access, name, descriptor, signature, exceptions) {
							@Override
							public void visitMaxs(int maxStack, int maxLocals) {
								super.visitMaxs(entry.getValue(), maxLocals);
							}
						};
						methods.add(method);
						return method;
					}
				}
				return super.visitMethod(access, name, descriptor, signature, exceptions);
			}
		};
		TRANSFORMERS.put(className, new StormClassTransformer(className, visitor) {

			@Override
			StormClassTransformer transform() {

				hook.installHook(this);
				return this;
			}
		});
	}

	/**
	 * Create and register a new {@link StormClassTransformer} with given name
	 * that installs a {@link StormHook} designated by method parameter.
	 *
	 * @param className name of the target class to transform.
	 * @param hook {@link StormHook} to install with transformation.
	 */
	private static void registerTransformer(String className, StormHook hook) {

		TRANSFORMERS.put(className, new StormClassTransformer(className) {

			@Override
			StormClassTransformer transform() {

				hook.installHook(this);
				return this;
			}
		});
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

	private static class MethodData {

		private final String name;
		private final String descriptor;

		private MethodData(String name, String descriptor) {
			this.name = name;
			this.descriptor = descriptor;
		}
	}
}
