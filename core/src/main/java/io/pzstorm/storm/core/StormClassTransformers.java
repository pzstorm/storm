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

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import com.google.common.collect.ImmutableMap;

import io.pzstorm.storm.hook.*;
import io.pzstorm.storm.patch.DebugLogPatch;
import io.pzstorm.storm.patch.DebugLogStreamPatch;
import io.pzstorm.storm.patch.GameWindowPatch;
import io.pzstorm.storm.patch.ZomboidPatch;

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
		registerTransformer("zombie.Lua.LuaEventManager",
				new OnTriggerLuaEventHook(), ImmutableMap.<MethodData, MethodMaxs>builder()
						.put(new MethodData("triggerEvent",
										"(Ljava/lang/String;Ljava/lang/Object;)V"),
								new MethodMaxs(7))
						.put(new MethodData("triggerEvent",
										"(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V"),
								new MethodMaxs(7))
						.put(new MethodData("triggerEvent",
										"(Ljava/lang/String;Ljava/lang/Object;" +
												"Ljava/lang/Object;Ljava/lang/Object;)V"),
								new MethodMaxs(7))
						.put(new MethodData("triggerEvent",
										"(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;" +
												"Ljava/lang/Object;Ljava/lang/Object;)V"),
								new MethodMaxs(7))
						.put(new MethodData("triggerEvent",
										"(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;" +
												"Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V"),
								new MethodMaxs(7))
						.put(new MethodData("triggerEvent",
										"(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;" +
												"Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)" +
												"V"),
								new MethodMaxs(7))
						.build()
		);
		registerTransformer("zombie.GameWindow", new GameWindowPatch());
		registerTransformer("fmod.javafmod",
				new OnLoadSoundBankHook(), ImmutableMap.<MethodData, MethodMaxs>builder()
						.put(new MethodData("FMOD_Studio_System_LoadBankFile",
								"(Ljava/lang/String;)J"), new MethodMaxs(3, 2))
						.build()
		);
		registerTransformer("fmod.fmod.FMODManager", new OnLoadSoundBanksHook());

		///////////////////////
		// REGISTER PATCHES //
		/////////////////////

		registerTransformer("zombie.debug.DebugLog", new DebugLogPatch());
		registerTransformer("zombie.debug.DebugLogStream", new DebugLogStreamPatch(),
				ImmutableMap.<MethodData, MethodMaxs>builder()
						.put(new MethodData("printException",
										"(Ljava/lang/Throwable;Ljava/lang/String;" +
												"Ljava/lang/String;Lzombie/debug/LogSeverity;)V"),
								new MethodMaxs(5, 6))
						.build()
		);
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
	 * Create and register a new {@link StormClassTransformer} with given name that applies
	 * a {@link ZomboidPatch} designated by method parameter. Additionally this method
	 * also defines the maximum stack size of methods in visited class.
	 *
	 * @param className name of the target class to transform.
	 * @param patch {@code ZomboidPatch} to apply with transformation.
	 * @param maxStacks maximum stack size mapped to method data.
	 */
	private static void registerTransformer(String className, ZomboidPatch patch,
											Map<MethodData, MethodMaxs> maxStacks) {

		ClassNode visitor = new ClassNode(Opcodes.ASM9) {

			@Override
			public MethodVisitor visitMethod(int access, String name, String descriptor,
											 String signature, String[] exceptions) {

				for (Map.Entry<MethodData, MethodMaxs> entry : maxStacks.entrySet())
				{
					MethodData data = entry.getKey();
					if (name.equals(data.name) && descriptor.equals(data.descriptor))
					{
						MethodMaxs maxData = entry.getValue();
						MethodNode method = new MethodNode(Opcodes.ASM9, access, name, descriptor, signature
								, exceptions) {
							@Override
							public void visitMaxs(int maxStack, int maxLocals) {
								super.visitMaxs(maxData.maxStack > 0 ? maxData.maxStack : maxStack,
										maxData.maxLocal > 0 ? maxData.maxLocal : maxLocals);
							}
						};
						methods.add(method);
						return method;
					}
				}
				return super.visitMethod(access, name, descriptor, signature, exceptions);
			}
		};
		registerTransformer(className, patch);
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
	private static void registerTransformer(String className, StormHook hook, Map<MethodData, MethodMaxs> maxStacks) {

		ClassNode visitor = new ClassNode(Opcodes.ASM9) {

			@Override
			public MethodVisitor visitMethod(int access, String name, String descriptor,
											 String signature, String[] exceptions) {

				for (Map.Entry<MethodData, MethodMaxs> entry : maxStacks.entrySet())
				{
					MethodData data = entry.getKey();
					if (name.equals(data.name) && descriptor.equals(data.descriptor))
					{
						MethodMaxs maxData = entry.getValue();
						MethodNode method = new MethodNode(Opcodes.ASM9, access, name, descriptor, signature, exceptions) {
							@Override
							public void visitMaxs(int maxStack, int maxLocals) {
								super.visitMaxs(maxData.maxStack > 0 ? maxData.maxStack : maxStack,
										maxData.maxLocal > 0 ? maxData.maxLocal : maxLocals);
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

	private static class MethodMaxs {

		/**
		 * <p>Maximum stack size of the method.</p>
		 * Value of {@code 0} indicates that original value should be used.
		 */
		private final int maxStack;

		/**
		 * <p>Maximum number of local variables for the method.</p>
		 * Value of {@code 0} indicates that original value should be used.
		 */
		private final int maxLocal;

		private MethodMaxs(int maxStack, int maxLocal) {

			this.maxStack = maxStack;
			this.maxLocal = maxLocal;
		}

		private MethodMaxs(int maxStack) {

			this.maxStack = maxStack;
			this.maxLocal = 0;
		}
	}
}
