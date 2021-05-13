package io.pzstorm.storm.patch;

import java.util.Objects;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import com.google.common.collect.ImmutableList;

import io.pzstorm.storm.core.StormClassTransformer;
import io.pzstorm.storm.util.AsmUtils;

public class DebugLogPatch implements ZomboidPatch {

	@Override
	public void applyPatch(StormClassTransformer transformer) {

		// String formatStringVarArgs(
		//	 	DebugType var0, LogSeverity var1, String var2, Object var3, String var4, Object... var5)
		InsnList formatStringVarArgs = transformer.getInstructionsForMethod("formatStringVarArgs",
				"(Lzombie/debug/DebugType;Lzombie/debug/LogSeverity;Ljava/lang/String;" +
						"Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;");

		// clear all instructions and rewrite method from scratch
		formatStringVarArgs.clear();

		// apply custom formatting that is compatible with log4j2 layout pattern
		// example: [01:03:21] [INFO] [Zomboid/General]: log message printed here
		LabelNode labelAfterIF = new LabelNode();
		AsmUtils.addToInsnList(formatStringVarArgs,
				new LabelNode(),
				new VarInsnNode(Opcodes.ALOAD, 1),
				new VarInsnNode(Opcodes.ALOAD, 0),
				new MethodInsnNode(Opcodes.INVOKESTATIC, "zombie/debug/DebugLog",
						"isLogEnabled", "(Lzombie/debug/LogSeverity;Lzombie/debug/DebugType;)Z"),
				new JumpInsnNode(Opcodes.IFNE, labelAfterIF),
				new LabelNode(),
				new InsnNode(Opcodes.ACONST_NULL),
				new InsnNode(Opcodes.ARETURN),
				labelAfterIF,
				new FrameNode(Opcodes.F_SAME, 0, null, 0, null),
				new TypeInsnNode(Opcodes.NEW, "java/lang/StringBuilder"),
				new InsnNode(Opcodes.DUP),
				new MethodInsnNode(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V"),
				new VarInsnNode(Opcodes.ALOAD, 0),
				new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder",
						"append", "(Ljava/lang/Object;)Ljava/lang/StringBuilder;"),
				new LdcInsnNode("]: "),
				new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder",
						"append", "(Ljava/lang/Object;)Ljava/lang/StringBuilder;"),
				new VarInsnNode(Opcodes.ALOAD, 4),
				new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder",
						"append", "(Ljava/lang/Object;)Ljava/lang/StringBuilder;"),
				new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder",
						"toString", "()Ljava/lang/String;"),
				new VarInsnNode(Opcodes.ALOAD, 5),
				new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/String",
						"format", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;"),
				new InsnNode(Opcodes.ARETURN)
		);
		/* though most of the logging is handled by DebugLogStream
		 * some logs are printed by DebugLog#log(String) method
		 */
		InsnList log = transformer.getInstructionsForMethod(
				"log", "(Lzombie/debug/DebugType;Ljava/lang/String;)V"
		);
		LabelNode node = Objects.requireNonNull(AsmUtils.getNthLabelNode(log, 3));
		AsmUtils.replaceFirstMatchingLabelNode(log, node, ImmutableList.of(
				new VarInsnNode(Opcodes.ALOAD, 2), new MethodInsnNode(Opcodes.INVOKESTATIC,
						"io/pzstorm/storm/logging/ZomboidLogger", "info", "(Ljava/lang/String;)V"))
		);
	}
}
