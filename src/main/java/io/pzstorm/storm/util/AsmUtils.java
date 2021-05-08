package io.pzstorm.storm.util;

import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.tree.*;

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

	/**
	 * Finds and returns the last instructions of a specific {@code Class} in a given list.
	 *
	 * @param list list of instructions to search.
	 * @param node {@link AbstractInsnNode} to match.
	 * @param <T> type of instruction to match.
	 */
	@SuppressWarnings("unchecked")
	public static @Nullable <T extends AbstractInsnNode> T getLastInstructionOfType(InsnList list, Class<T> node) {

		AbstractInsnNode result = null;
		for (AbstractInsnNode instruction : list)
		{
			if (node.isInstance(instruction)) {
				result = instruction;
			}
		}
		return (T) result;
	}

	/**
	 * Find and return first {@link LabelNode} that contains given list of instructions.
	 * Note that {@link LineNumberNode} and {@link LabelNode} instructions will be excluded when
	 * comparing entries which means that these instructions are safe to be included in {@code match} list.
	 *
	 * @param list list of instructions to search.
	 * @param match list of instructions to match.
	 *
	 * @return first {@code LabelNode} that was matched or {@code null} if no node was matched.
	 */
	public static @Nullable LabelNode getFirstMatchingLabelNode(InsnList list, List<AbstractInsnNode> match) {

		//@formatter:off
		for (int i1 = 0; i1 < list.size(); i1++)
		{
			AbstractInsnNode instruction = list.get(i1);
			if (instruction instanceof LabelNode)
			{
				int i3 = i1 + 1;
				boolean matchedInstructions = true;
				for (int i2 = 0; i2 < match.size() && i3 < list.size(); i2++, i3++)
				{
					AbstractInsnNode a = list.get(i3);
					// ignore line number nodes
					if (!(a instanceof LineNumberNode))
					{
						AbstractInsnNode b = match.get(i2);
						// ignore label and line number nodes
						if (!(b instanceof LabelNode) && !(b instanceof LineNumberNode))
						{
							if (!AsmUtils.equalNodes(a, b)) {
								matchedInstructions = false; break;
							}
						}
						else i3 -= 1;	// when ignoring nodes counter variables
					}					// need to be adjusted to compensate for
					else i2 -= 1;		// the for-loop auto-incremental operation
				}
				if (matchedInstructions) {
					return (LabelNode) instruction;
				}
			}
		}//@formatter:on
		return null;
	}

	/**
	 * Compare the given nodes and return {@code true} if they are equal.
	 * Since instances of {@link AbstractInsnNode} do not implement a equality comparison,
	 * comparing two node instances will compare object addresses and omit comparing fields.
	 * This method does a deep comparison, comparing instance fields.
	 *
	 * @param a first node to compare.
	 * @param b second node to compare.
	 *
	 * @return {@code true} if node fields are equal, {@code false} otherwise.
	 */
	public static boolean equalNodes(AbstractInsnNode a, AbstractInsnNode b) {

		if (!a.getClass().isInstance(b)) {
			return false;
		}
		if (a.getType() != b.getType()) {
			return false;
		}
		if (a.getOpcode() != b.getOpcode()) {
			return false;
		}
		if (a instanceof FieldInsnNode)
		{
			return ((FieldInsnNode) a).owner.equals(((FieldInsnNode) b).owner) &&
					((FieldInsnNode) a).name.equals(((FieldInsnNode) b).name) &&
					((FieldInsnNode) a).desc.equals(((FieldInsnNode) b).desc);
		}
		if (a instanceof FrameNode)
		{
			return ((FrameNode) a).local.equals(((FrameNode) b).local) &&
					((FrameNode) a).stack.equals(((FrameNode) b).stack);
		}
		if (a instanceof IincInsnNode)
		{
			return ((IincInsnNode) a).var == ((IincInsnNode) b).var &&
					((IincInsnNode) a).incr == ((IincInsnNode) b).incr;
		}
		if (a instanceof IntInsnNode) {
			return ((IntInsnNode) a).operand == ((IntInsnNode) b).operand;
		}
		if (a instanceof InvokeDynamicInsnNode)
		{
			return ((InvokeDynamicInsnNode) a).name.equals(((InvokeDynamicInsnNode) b).name) &&
					((InvokeDynamicInsnNode) a).desc.equals(((InvokeDynamicInsnNode) b).desc) &&
					((InvokeDynamicInsnNode) a).bsm.equals(((InvokeDynamicInsnNode) b).bsm) &&
					Arrays.equals(((InvokeDynamicInsnNode) a).bsmArgs, ((InvokeDynamicInsnNode) b).bsmArgs);
		}
		if (a instanceof JumpInsnNode) {
			return AsmUtils.equalNodes(((JumpInsnNode) a).label, ((JumpInsnNode) b).label);
		}
		if (a instanceof LabelNode) {
			return ((LabelNode) a).getLabel().equals(((LabelNode) b).getLabel());
		}
		if (a instanceof LdcInsnNode) {
			return ((LdcInsnNode) a).cst.equals(((LdcInsnNode) b).cst);
		}
		if (a instanceof LineNumberNode)
		{
			return ((LineNumberNode) a).line == ((LineNumberNode) b).line &&
					AsmUtils.equalNodes(((LineNumberNode) a).start, ((LineNumberNode) b).start);
		}
		if (a instanceof LookupSwitchInsnNode)
		{
			return (AsmUtils.equalNodes(((LookupSwitchInsnNode) a).dflt, ((LookupSwitchInsnNode) b).dflt) &&
					((LookupSwitchInsnNode) a).keys.equals(((LookupSwitchInsnNode) b).keys) &&
					((LookupSwitchInsnNode) a).labels.equals(((LookupSwitchInsnNode) b).labels));
		}
		if (a instanceof MethodInsnNode)
		{
			return ((MethodInsnNode) a).owner.equals(((MethodInsnNode) b).owner) &&
					((MethodInsnNode) a).name.equals(((MethodInsnNode) b).name) &&
					((MethodInsnNode) a).desc.equals(((MethodInsnNode) b).desc) &&
					((MethodInsnNode) a).itf == ((MethodInsnNode) b).itf;
		}
		if (a instanceof MultiANewArrayInsnNode)
		{
			return ((MultiANewArrayInsnNode) a).desc.equals(((MultiANewArrayInsnNode) b).desc) &&
					((MultiANewArrayInsnNode) a).dims == ((MultiANewArrayInsnNode) b).dims;
		}
		if (a instanceof TableSwitchInsnNode)
		{
			if (((TableSwitchInsnNode) a).min != ((TableSwitchInsnNode) b).min) {
				return false;
			}
			if (((TableSwitchInsnNode) a).max != ((TableSwitchInsnNode) b).max) {
				return false;
			}
			if (!AsmUtils.equalNodes(((TableSwitchInsnNode) a).dflt, ((TableSwitchInsnNode) b).dflt)) {
				return false;
			}
			List<LabelNode> aLabelNodes = ((TableSwitchInsnNode) a).labels;
			List<LabelNode> bLabelNodes = ((TableSwitchInsnNode) b).labels;
			if (aLabelNodes.size() != bLabelNodes.size()) {
				return false;
			}
			for (int i = 0; i < ((TableSwitchInsnNode) a).labels.size(); i++)
			{
				if (!AsmUtils.equalNodes(aLabelNodes.get(i), bLabelNodes.get(i))) {
					return false;
				}
			}
		}
		if (a instanceof TypeInsnNode) {
			return ((TypeInsnNode) a).desc.equals(((TypeInsnNode) b).desc);
		}
		if (a instanceof VarInsnNode) {
			return ((VarInsnNode) a).var == ((VarInsnNode) b).var;
		}
		return true;
	}
}
