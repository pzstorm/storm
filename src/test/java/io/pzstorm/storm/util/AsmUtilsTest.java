package io.pzstorm.storm.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import io.pzstorm.storm.UnitTest;

class AsmUtilsTest implements UnitTest {

	private static final String intDesc = "(I)Ljava/lang/Integer;";
	private static final LabelNode[] LABELS = new LabelNode[] {
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
	void shouldAddInstructionsToTheEndOfInsnList() {

		AbstractInsnNode[] nodesToAdd = new AbstractInsnNode[] {
				new LabelNode(),
				new VarInsnNode(Opcodes.ALOAD, 0),
				new MethodInsnNode(Opcodes.INVOKESPECIAL,
						"io.pzstorm.storm.util", "<init>", "()V"),
				new InsnNode(Opcodes.RETURN)
		};
		InsnList expectedList = createInstructionList();
		for (AbstractInsnNode node : nodesToAdd) {
			expectedList.add(node);
		}
		InsnList actualList = AsmUtils.addToInsnList(
				createInstructionList(),
				new LabelNode(),
				new VarInsnNode(Opcodes.ALOAD, 0),
				new MethodInsnNode(Opcodes.INVOKESPECIAL,
						"io.pzstorm.storm.util", "<init>", "()V"),
				new InsnNode(Opcodes.RETURN)
		);;

		Assertions.assertEquals(expectedList.size(), actualList.size());

		Iterator<AbstractInsnNode> iteratorExpected = expectedList.iterator();
		Iterator<AbstractInsnNode> iteratorActual = expectedList.iterator();
		while (iteratorExpected.hasNext()) {
			Assertions.assertTrue(AsmUtils.equalNodes(iteratorExpected.next(), iteratorActual.next()));
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

	@Test
	void shouldGetFirstMatchingLabelNodeInstruction() {

		List<Integer[]> labelIndexPairs = ImmutableList.of(
				new Integer[] { 0, 6 }, new Integer[] { 6, 12 },
				new Integer[] { 12, 23 }, new Integer[] { 23, 27 }
		);
		for (int i1 = 0; i1 < labelIndexPairs.size(); i1++)
		{
			List<AbstractInsnNode> insnToMatch = new ArrayList<>();
			Integer[] labelIndexPair = labelIndexPairs.get(i1);
			for (int i2 = labelIndexPair[0]; i2 < labelIndexPair[1]; i2++) {
				insnToMatch.add(instructions.get(i2));
			}
			LabelNode expectedLabel = LABELS[i1];
			LabelNode actualLabel = AsmUtils.getFirstMatchingLabelNode(createInstructionList(), insnToMatch);
			Assertions.assertEquals(expectedLabel, actualLabel);
		}
	}

	@Test
	void shouldReplaceFirstMatchingLabelNodeInstructions() {

		// String a = String.valueOf(2 + 2);
		List<AbstractInsnNode> expectedList = ImmutableList.of(
				new InsnNode(Opcodes.ICONST_4),
				new MethodInsnNode(Opcodes.INVOKESTATIC,
						"java/lang/String", "valueOf", "(I)Ljava/lang/String;"),
				new VarInsnNode(Opcodes.ASTORE, 1)
		);
		InsnList instructions = createInstructionList();
		Assertions.assertTrue(AsmUtils.replaceFirstMatchingLabelNode(instructions, LABELS[1], expectedList));

		Iterator<AbstractInsnNode> iteratorA = instructions.iterator(7);
		for (AbstractInsnNode expectedNode : expectedList) {
			Assertions.assertTrue(AsmUtils.equalNodes(iteratorA.next(), expectedNode));
		}
		Assertions.assertTrue(iteratorA.next() instanceof LabelNode);
	}

	@Test
	void shouldCompareInstructionNodeFields() {
		// TODO: finish writing this test

		FieldInsnNode fia = new FieldInsnNode(Opcodes.GETSTATIC, "owner", "name", "desc");
		FieldInsnNode fib = new FieldInsnNode(Opcodes.GETSTATIC, "owner", "name", "desc");
		Assertions.assertTrue(AsmUtils.equalNodes(fia, fib));
	}
}
