package io.pzstorm.storm.core;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import com.google.common.collect.ImmutableList;

import io.pzstorm.storm.util.AsmUtils;

@SuppressWarnings("unused")
public class ZombieUtilsTransformer extends StormClassTestTransformer {

	public ZombieUtilsTransformer() {
		super("zombie.ZombieUtils");
	}

	@Override
	StormClassTransformer transform() {

		InsnList instructions = getInstructionsForMethod("setZombieProperties", "(IZ)V");
		AbstractInsnNode target = AsmUtils.getFirstMatchingLabelNode(instructions, ImmutableList.of(
				new VarInsnNode(Opcodes.ILOAD, 1),
				new FieldInsnNode(Opcodes.PUTSTATIC, "zombie/ZombieUtils", "zombiePropertyC", "Z")
		));
		instructions.insertBefore(target, AsmUtils.createInsnList(
				new LabelNode(), new LdcInsnNode("property"),
				new FieldInsnNode(Opcodes.PUTSTATIC, "zombie/ZombieUtils", "zombiePropertyB", "Ljava/lang/String;")
		));
		return this;
	}
}
