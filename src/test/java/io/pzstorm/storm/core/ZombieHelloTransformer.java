package io.pzstorm.storm.core;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LdcInsnNode;

public class ZombieHelloTransformer extends StormClassTransformer {

	public ZombieHelloTransformer() {
		super("zombie.ZombieHello");
	}

	@Override
	public StormClassTransformer transform() {

		InsnList instructions = getInstructionsForMethod("getHello", "()Ljava/lang/String;");
		for (AbstractInsnNode instruction : instructions)
		{
			if (instruction.getOpcode() == Opcodes.LDC) {
				((LdcInsnNode) instruction).cst = "Zombie says: you die today!";
			}
		}
		return this;
	}
}
