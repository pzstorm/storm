package io.pzstorm.storm.util;

import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.tree.*;

import java.util.Arrays;
import java.util.List;

/**
 * This class contains an assortment of helpful methods when working with ASM.
 */
public class AsmUtils {

	/**
	 * Create a new {@link InsnList} from given array of instructions.
	 *
	 * @param instructions array of instructions to use.
	 */
	public static InsnList createInsnList(AbstractInsnNode... instructions) {

		InsnList result = new InsnList();
		for (AbstractInsnNode instruction : instructions) {
			result.add(instruction);
		}
		return result;
	}
}
