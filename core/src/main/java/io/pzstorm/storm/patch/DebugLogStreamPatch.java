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

package io.pzstorm.storm.patch;

import java.util.List;
import java.util.Objects;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import com.google.common.collect.ImmutableList;

import io.pzstorm.storm.core.StormClassTransformer;
import io.pzstorm.storm.util.AsmUtils;

public class DebugLogStreamPatch implements ZomboidPatch {

	@Override
	public void applyPatch(StormClassTransformer transformer) {

		// private void write(PrintStream var1, String var2)
		replaceMethodNode(transformer.getInstructionsForMethod("write",
				"(Ljava/io/PrintStream;Ljava/lang/String;)V"), 3,
				ImmutableList.of(new VarInsnNode(Opcodes.ALOAD, 3), new MethodInsnNode(
						Opcodes.INVOKESTATIC, "io/pzstorm/storm/logging/ZomboidLogger",
						"info", "(Ljava/lang/String;)V"))
		);
		// private void writeln(PrintStream var1, LogSeverity var2, String var3, String var4)
		replaceMethodNode(transformer.getInstructionsForMethod("writeln",
				"(Ljava/io/PrintStream;Lzombie/debug/LogSeverity;Ljava/lang/String;Ljava/lang/String;)V"),
				3, ImmutableList.of(
						new VarInsnNode(Opcodes.ALOAD, 2),
						new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "zombie/debug/LogSeverity",
								"toString", "()Ljava/lang/String;"),
						new VarInsnNode(Opcodes.ALOAD, 5),
						new MethodInsnNode(Opcodes.INVOKESTATIC, "io/pzstorm/storm/logging/ZomboidLogger",
								"printf", "(Ljava/lang/String;Ljava/lang/String;)V"))
		);
		// private void writeln(PrintStream var1, LogSeverity var2, String var3, String var4, Object var5)
		replaceMethodNode(transformer.getInstructionsForMethod("writeln",
				"(Ljava/io/PrintStream;Lzombie/debug/LogSeverity;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V"),
				3, ImmutableList.of(
						new VarInsnNode(Opcodes.ALOAD, 2),
						new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "zombie/debug/LogSeverity",
								"toString", "()Ljava/lang/String;"),
						new VarInsnNode(Opcodes.ALOAD, 6),
						new MethodInsnNode(Opcodes.INVOKESTATIC, "io/pzstorm/storm/logging/ZomboidLogger",
								"printf", "(Ljava/lang/String;Ljava/lang/String;)V"))
		);
		// public void debugln(String var1)
		replaceMethodNode(transformer.getInstructionsForMethod("debugln",
				"(Ljava/lang/String;)V"),
				4, ImmutableList.of(new VarInsnNode(Opcodes.ALOAD, 3), new MethodInsnNode(
						Opcodes.INVOKESTATIC, "io/pzstorm/storm/logging/ZomboidLogger",
						"debug", "(Ljava/lang/String;)V"))
		);
		// public void debugln(String var1, Object var2)
		replaceMethodNode(transformer.getInstructionsForMethod("debugln",
				"(Ljava/lang/String;Ljava/lang/Object;)V"),
				4, ImmutableList.of(new VarInsnNode(Opcodes.ALOAD, 4), new MethodInsnNode(
						Opcodes.INVOKESTATIC, "io/pzstorm/storm/logging/ZomboidLogger",
						"debug", "(Ljava/lang/String;)V"))
		);
		// public void debugln(String var1, Object var2, Object var3)
		replaceMethodNode(transformer.getInstructionsForMethod("debugln",
				"(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V"),
				4, ImmutableList.of(new VarInsnNode(Opcodes.ALOAD, 5), new MethodInsnNode(
						Opcodes.INVOKESTATIC, "io/pzstorm/storm/logging/ZomboidLogger",
						"debug", "(Ljava/lang/String;)V"))
		);
		// public void debugln(String var1, Object var2, Object var3, Object var4)
		replaceMethodNode(transformer.getInstructionsForMethod("debugln",
				"(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V"),
				4, ImmutableList.of(new VarInsnNode(Opcodes.ALOAD, 6), new MethodInsnNode(
						Opcodes.INVOKESTATIC, "io/pzstorm/storm/logging/ZomboidLogger",
						"debug", "(Ljava/lang/String;)V"))
		);
		// public void debugln(String var1, Object var2, Object var3, Object var4, Object var5)
		replaceMethodNode(transformer.getInstructionsForMethod("debugln",
				"(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;" +
						"Ljava/lang/Object;)V"),
				4, ImmutableList.of(new VarInsnNode(Opcodes.ALOAD, 7), new MethodInsnNode(
						Opcodes.INVOKESTATIC, "io/pzstorm/storm/logging/ZomboidLogger",
						"debug", "(Ljava/lang/String;)V"))
		);
		// public void debugln(String var1, Object var2, Object var3, Object var4, Object var5, Object var6)
		replaceMethodNode(transformer.getInstructionsForMethod("debugln",
				"(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;" +
						"Ljava/lang/Object;Ljava/lang/Object;)V"),
				4, ImmutableList.of(new VarInsnNode(Opcodes.ALOAD, 8), new MethodInsnNode(
						Opcodes.INVOKESTATIC, "io/pzstorm/storm/logging/ZomboidLogger",
						"debug", "(Ljava/lang/String;)V"))
		);
		// public void debugln(String var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7)
		replaceMethodNode(transformer.getInstructionsForMethod("debugln",
				"(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;" +
						"Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V"),
				4, ImmutableList.of(new VarInsnNode(Opcodes.ALOAD, 9), new MethodInsnNode(
						Opcodes.INVOKESTATIC, "io/pzstorm/storm/logging/ZomboidLogger",
						"debug", "(Ljava/lang/String;)V"))
		);
		// public void println(String var1, Object var2)
		replaceMethodNode(transformer.getInstructionsForMethod("println",
				"(Ljava/lang/String;Ljava/lang/Object;)V"),
				3, ImmutableList.of(new VarInsnNode(Opcodes.ALOAD, 3), new MethodInsnNode(
						Opcodes.INVOKESTATIC, "io/pzstorm/storm/logging/ZomboidLogger",
						"info", "(Ljava/lang/String;)V"))
		);
		// public void println(String var1, Object var2, Object var3)
		replaceMethodNode(transformer.getInstructionsForMethod("println",
				"(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V"),
				3, ImmutableList.of(new VarInsnNode(Opcodes.ALOAD, 4), new MethodInsnNode(
						Opcodes.INVOKESTATIC, "io/pzstorm/storm/logging/ZomboidLogger",
						"info", "(Ljava/lang/String;)V"))
		);
		// public void println(String var1, Object var2, Object var3, Object var4)
		replaceMethodNode(transformer.getInstructionsForMethod("println",
				"(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V"),
				3, ImmutableList.of(new VarInsnNode(Opcodes.ALOAD, 5), new MethodInsnNode(
						Opcodes.INVOKESTATIC, "io/pzstorm/storm/logging/ZomboidLogger",
						"info", "(Ljava/lang/String;)V"))
		);
		// public void println(String var1, Object var2, Object var3, Object var4, Object var5)
		replaceMethodNode(transformer.getInstructionsForMethod("println",
				"(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;" +
						"Ljava/lang/Object;)V"),
				3, ImmutableList.of(new VarInsnNode(Opcodes.ALOAD, 6), new MethodInsnNode(
						Opcodes.INVOKESTATIC, "io/pzstorm/storm/logging/ZomboidLogger",
						"info", "(Ljava/lang/String;)V"))
		);
		// public void println(String var1, Object var2, Object var3, Object var4, Object var5, Object var6)
		replaceMethodNode(transformer.getInstructionsForMethod("println",
				"(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;" +
						"Ljava/lang/Object;Ljava/lang/Object;)V"),
				3, ImmutableList.of(new VarInsnNode(Opcodes.ALOAD, 7), new MethodInsnNode(
						Opcodes.INVOKESTATIC, "io/pzstorm/storm/logging/ZomboidLogger",
						"info", "(Ljava/lang/String;)V"))
		);
		// public void println(String var1, Object var2, Object var3, Object var4, Object var5, Object var6,
		// 						Object var7)
		replaceMethodNode(transformer.getInstructionsForMethod("println",
				"(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;" +
						"Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V"),
				3, ImmutableList.of(new VarInsnNode(Opcodes.ALOAD, 8), new MethodInsnNode(
						Opcodes.INVOKESTATIC, "io/pzstorm/storm/logging/ZomboidLogger",
						"info", "(Ljava/lang/String;)V"))
		);
		// public void println(String var1, Object var2, Object var3, Object var4, Object var5, Object var6,
		// 						Object var7, Object var8)
		replaceMethodNode(transformer.getInstructionsForMethod("println",
				"(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;" +
						"Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V"),
				3, ImmutableList.of(new VarInsnNode(Opcodes.ALOAD, 9), new MethodInsnNode(
						Opcodes.INVOKESTATIC, "io/pzstorm/storm/logging/ZomboidLogger",
						"info", "(Ljava/lang/String;)V"))
		);
		// public void println(String var1, Object var2, Object var3, Object var4, Object var5, Object var6,
		// 						Object var7, Object var8, Object var9)
		replaceMethodNode(transformer.getInstructionsForMethod("println",
				"(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;" +
						"Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;" +
						"Ljava/lang/Object;)V"),
				3, ImmutableList.of(new VarInsnNode(Opcodes.ALOAD, 10), new MethodInsnNode(
						Opcodes.INVOKESTATIC, "io/pzstorm/storm/logging/ZomboidLogger",
						"info", "(Ljava/lang/String;)V"))
		);
		// public void println(String var1, Object var2, Object var3, Object var4, Object var5, Object var6,
		// 						Object var7, Object var8, Object var9, Object var10)
		replaceMethodNode(transformer.getInstructionsForMethod("println",
				"(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;" +
						"Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;" +
						"Ljava/lang/Object;Ljava/lang/Object;)V"),
				3, ImmutableList.of(new VarInsnNode(Opcodes.ALOAD, 11), new MethodInsnNode(
						Opcodes.INVOKESTATIC, "io/pzstorm/storm/logging/ZomboidLogger",
						"info", "(Ljava/lang/String;)V"))
		);
		// public void printException(Throwable var1, String var2, String var3, LogSeverity var4)
		InsnList printException = transformer.getInstructionsForMethod("printException",
				"(Ljava/lang/Throwable;Ljava/lang/String;Ljava/lang/String;Lzombie/debug/LogSeverity;)V"
		);
		printException.clear();

		LabelNode switch1 = new LabelNode();
		LabelNode switch2 = new LabelNode();
		LabelNode switch3 = new LabelNode();
		LabelNode switchDefault = new LabelNode();
		LabelNode label6 = new LabelNode();

		AsmUtils.addToInsnList(printException, new LabelNode(),
				new FieldInsnNode(Opcodes.GETSTATIC,
				"zombie/debug/DebugLogStream$1", "$SwitchMap$zombie$debug$LogSeverity", "[I"),
				new VarInsnNode(Opcodes.ALOAD, 4),
				new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "zombie/debug/LogSeverity",
						"ordinal", "()I"
				),
				new InsnNode(Opcodes.IALOAD),
				new TableSwitchInsnNode(1, 4, switchDefault, switch1, switch1, switch2, switch3),
				switch1, new FrameNode(Opcodes.F_SAME, 0, null, 0, null),
				new MethodInsnNode(Opcodes.INVOKESTATIC, "io/pzstorm/storm/logging/ZomboidLogger",
						"get", "()Lorg/apache/logging/log4j/Logger;"),
				new VarInsnNode(Opcodes.ALOAD, 2),
				new VarInsnNode(Opcodes.ALOAD, 1),
				new MethodInsnNode(Opcodes.INVOKEINTERFACE, "org/apache/logging/log4j/Logger",
						"info", "(Ljava/lang/String;Ljava/lang/Throwable;)V"),
				new LabelNode(),
				new JumpInsnNode(Opcodes.GOTO, label6),
				switch2, new FrameNode(Opcodes.F_SAME, 0, null, 0, null),
				new MethodInsnNode(Opcodes.INVOKESTATIC, "io/pzstorm/storm/logging/ZomboidLogger",
						"get", "()Lorg/apache/logging/log4j/Logger;"),
				new VarInsnNode(Opcodes.ALOAD, 2),
				new VarInsnNode(Opcodes.ALOAD, 1),
				new MethodInsnNode(Opcodes.INVOKEINTERFACE, "org/apache/logging/log4j/Logger",
						"warn", "(Ljava/lang/String;Ljava/lang/Throwable;)V"),
				new LabelNode(),
				new JumpInsnNode(Opcodes.GOTO, label6),
				switch3, new FrameNode(Opcodes.F_SAME, 0, null, 0, null),
				new MethodInsnNode(Opcodes.INVOKESTATIC, "io/pzstorm/storm/logging/ZomboidLogger",
						"get", "()Lorg/apache/logging/log4j/Logger;"),
				new VarInsnNode(Opcodes.ALOAD, 2),
				new VarInsnNode(Opcodes.ALOAD, 1),
				new MethodInsnNode(Opcodes.INVOKEINTERFACE, "org/apache/logging/log4j/Logger",
						"error", "(Ljava/lang/String;Ljava/lang/Throwable;)V"),
				new LabelNode(),
				new JumpInsnNode(Opcodes.GOTO, label6),
				switchDefault, new FrameNode(Opcodes.F_SAME, 0, null, 0, null),
				new LdcInsnNode("Unhandled exception was thrown (%s)"),
				new InsnNode(Opcodes.ICONST_1),
				new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/Object"),
				new InsnNode(Opcodes.DUP),
				new InsnNode(Opcodes.ICONST_0),
				new VarInsnNode(Opcodes.ALOAD, 4),
				new InsnNode(Opcodes.AASTORE),
				new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/String",
						"format", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;"),
				new VarInsnNode(Opcodes.ASTORE, 5),
				new LabelNode(),
				new MethodInsnNode(Opcodes.INVOKESTATIC, "io/pzstorm/storm/logging/ZomboidLogger",
						"get", "()Lorg/apache/logging/log4j/Logger;"),
				new VarInsnNode(Opcodes.ALOAD, 5),
				new VarInsnNode(Opcodes.ALOAD, 1),
				new MethodInsnNode(Opcodes.INVOKEINTERFACE, "org/apache/logging/log4j/Logger",
						"error", "(Ljava/lang/String;Ljava/lang/Throwable;)V"),
				label6, new FrameNode(Opcodes.F_SAME, 0, null, 0, null),
				new InsnNode(Opcodes.RETURN)
		);
	}

	private void replaceMethodNode(InsnList instructions, int labelIndex, List<AbstractInsnNode> content) {

		LabelNode node = Objects.requireNonNull(AsmUtils.getNthLabelNode(instructions, labelIndex));
		AsmUtils.replaceFirstMatchingLabelNode(instructions, node, content);
	}
}
