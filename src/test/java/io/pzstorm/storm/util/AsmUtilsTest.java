package io.pzstorm.storm.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.pzstorm.storm.UnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class AsmUtilsTest implements UnitTest {

	private static final String intDesc = "(I)Ljava/lang/Integer;";
	private static final LabelNode[] LABELS = new LabelNode[]{
			new LabelNode(), new LabelNode(), new LabelNode(),
			new LabelNode(), new LabelNode()
	};
	private static final AbstractInsnNode LAST_METHOD_NODE =
			new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", intDesc);
	private static final AbstractInsnNode LAST_LINE_NODE = new LineNumberNode(25, LABELS[2]);
	private static final AbstractInsnNode LAST_VAR_NODE = new VarInsnNode(Opcodes.ALOAD, 1);

	private final InsnList instructions = createInstructionList();
	private static InsnList createInstructionList() {

		InsnList result = new InsnList();
		/* L0
		 *   LINENUMBER 20 L0
		 *   NEW java/util/Random
		 *   DUP
		 *   INVOKESPECIAL java/util/Random.<init> ()V
		 *   ASTORE 0
		 */
		result.add(LABELS[0]);
		result.add(new LineNumberNode(20, LABELS[0]));
		result.add(new TypeInsnNode(Opcodes.NEW, "java/util/Random"));
		result.add(new InsnNode(Opcodes.POP));
		result.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "java/util/Random", "<init>", "()V"));
		result.add(new VarInsnNode(Opcodes.ASTORE, 0));
		/*
		 * L1
		 *   LINENUMBER 22 L1
		 *   ALOAD 0
		 *   INVOKEVIRTUAL java/util/Random.nextInt ()I
		 *   INVOKESTATIC java/lang/Integer.valueOf (I)Ljava/lang/Integer;
		 *   ASTORE 1
		 */
		result.add(LABELS[1]);
		result.add(new LineNumberNode(22, LABELS[1]));
		result.add(new VarInsnNode(Opcodes.ALOAD, 0));
		result.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/util/Random", "nextInt", "()I"));
		result.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", intDesc));
		result.add(new VarInsnNode(Opcodes.ASTORE, 1));
		/*
		 * L2
		 *   LINENUMBER 23 L2
		 *   ALOAD 1
		 *   INVOKEVIRTUAL java/lang/Integer.intValue ()I
		 *   NEW java/util/Random
		 *   DUP
		 *   INVOKESPECIAL java/util/Random.<init> ()V
		 *   INVOKEVIRTUAL java/util/Random.nextInt ()I
		 *   IADD
		 *   INVOKESTATIC java/lang/Integer.valueOf (I)Ljava/lang/Integer;
		 *   ASTORE 1
		 */
		result.add(LABELS[2]);
		result.add(new LineNumberNode(23, LABELS[2]));
		result.add(new VarInsnNode(Opcodes.ALOAD, 1));
		result.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I"));
		result.add(new TypeInsnNode(Opcodes.NEW, "java/util/Random"));
		result.add(new InsnNode(Opcodes.DUP));
		result.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "java/util/Random", "<init>", "()V"));
		result.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/util/Random", "nextInt", "()I"));
		result.add(new InsnNode(Opcodes.IADD));
		result.add(LAST_METHOD_NODE);
		result.add(new VarInsnNode(Opcodes.ASTORE, 1));
		/*
		 * L3
		 *   LINENUMBER 25 L3
		 *   ALOAD 1
		 *   ARETURN
		 */
		result.add(LABELS[3]);
		result.add(LAST_LINE_NODE);
		result.add(LAST_VAR_NODE);
		result.add(new InsnNode(Opcodes.ARETURN));
		result.add(LABELS[4]);

		return result;
	}

	@Test
	void shouldCreateInstructionListFromArguments() {

		AbstractInsnNode[] insnList = new AbstractInsnNode[instructions.size()];
		for (int i = 0; i < instructions.size(); i++) {
			insnList[i] = instructions.get(i);
 		}
		InsnList actualList = AsmUtils.createInsnList(insnList);
		for (int i = 0; i < actualList.size(); i++) {
			Assertions.assertEquals(instructions.get(i), actualList.get(i));
		}
	}

	@Test
	@SuppressWarnings("ConstantConditions")
	void shouldGetLastInstructionOfType() {

		Map<AbstractInsnNode, AbstractInsnNode> instructionsOfType = ImmutableMap.of(
				LABELS[4], AsmUtils.getLastInstructionOfType(instructions, LabelNode.class),
				LAST_METHOD_NODE, AsmUtils.getLastInstructionOfType(instructions, MethodInsnNode.class),
				LAST_LINE_NODE, AsmUtils.getLastInstructionOfType(instructions, LineNumberNode.class),
				LAST_VAR_NODE, AsmUtils.getLastInstructionOfType(instructions, VarInsnNode.class)
		);
		for (Map.Entry<AbstractInsnNode, AbstractInsnNode> entry : instructionsOfType.entrySet()) {
			Assertions.assertEquals(entry.getKey(), entry.getValue());
		}
	}
}
