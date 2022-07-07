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

package io.pzstorm.storm.util;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

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
	 * Add the specified array of instructions to the end of given {@link InsnList}.
	 *
	 * @param list instruction list to add the array of instructions to.
	 * @param instructions array of instructions to add to end of list.
	 *
	 * @return given instruction list with instructions added to end.
	 */
	public static InsnList addToInsnList(InsnList list, AbstractInsnNode... instructions) {

		for (AbstractInsnNode instruction : instructions) {
			list.add(instruction);
		}
		return list;
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
	 * Return {@code nth} {@link LabelNode} in given list of instructions.
	 *
	 * @param list {@link InsnList} to get the label node from.
	 * @param n index of the node to retrieve from list. The value of the index
	 * 		has to larger then {@code 0} otherwise an exception will be thrown.
	 *
	 * @return {@code LabelNode} found under the specified index or {@code null}
	 * 		if no {@code LabelNode} for that index was found.
	 *
	 * @throws IllegalArgumentException if node index is a number lower then {@code 1}.
	 */
	public static @Nullable LabelNode getNthLabelNode(InsnList list, int n) {

		if (n < 1) {
			throw new IllegalArgumentException("Method parameter 'n' has a value < 1");
		}
		//@formatter:off
		int i1 = 1; for (int i2 = 0; i2 < list.size(); i2++)
		{
			AbstractInsnNode node = list.get(i2);
			if (node instanceof LabelNode)
			{
				if (i1 == n) {
					return (LabelNode) node;
				}
				else i1 += 1;
			}
		}//@formatter:on
		return null;
	}

	public static AbstractInsnNode nextNonLabelLineNumberNode(AbstractInsnNode node) {
		if(node.getNext() == null) {
			return node;
		}
		if(node.getNext() instanceof LabelNode || node.getNext() instanceof LineNumberNode || node.getNext() instanceof FrameNode) {
			return nextNonLabelLineNumberNode(node.getNext());
		}
		return node.getNext();
	}

	public static List<AbstractInsnNode> listWithoutLabelAndLineNumberNodes(List<AbstractInsnNode> nodes) {
		return nodes.stream().filter(x->!(x instanceof LabelNode))
				.filter(x-> !(x instanceof LineNumberNode))
				.collect(Collectors.toList());
	}

	public static boolean matches(AbstractInsnNode node, List<AbstractInsnNode> match) {
		if(match.size() == 0) {
			return true;
		}
		if(node != null && AsmUtils.equalNodes(node, match.get(0))) {
			return matches(nextNonLabelLineNumberNode(node), match.subList(1, match.size()));
		}
		return false;
	}

	public static @Nullable AbstractInsnNode getFirstNode(InsnList list, List<AbstractInsnNode> match) {

		List<AbstractInsnNode> matchList = listWithoutLabelAndLineNumberNodes(match);
		for(AbstractInsnNode instruction  : list.toArray()) {
			if(instruction != null && AsmUtils.equalNodes(instruction, matchList.get(0))) {
				if(matches(instruction.getNext(), matchList.subList(1, matchList.size()))) {
					return instruction;
				}
			}
		}
		return null;
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

		List<AbstractInsnNode> matchList = listWithoutLabelAndLineNumberNodes(match);
		for(AbstractInsnNode instruction  : list.toArray()) {
			if(instruction instanceof LabelNode) {
				AbstractInsnNode firstNonLabelInstruction = nextNonLabelLineNumberNode(instruction);
				if(firstNonLabelInstruction != null && AsmUtils.equalNodes(firstNonLabelInstruction, matchList.get(0))) {
					if(matches(firstNonLabelInstruction.getNext(), matchList.subList(1, matchList.size()))) {
						return (LabelNode) instruction;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Replace all instructions between the given {@link LabelNode} and next label node in
	 * specified list of instructions. Note that label nodes will not be altered in any way,
	 * they are only used to mark the start and end of replacement operation.
	 *
	 * @param list list of instructions to replace nodes in.
	 * @param target {@link LabelNode} that marks the start of replacement.
	 * @param content list of instructions to insert after target node.
	 *
	 * @return {@code true} if replacement was successful, {@code false} otherwise.
	 *///@formatter:off
	public static boolean replaceFirstMatchingLabelNode(InsnList list, LabelNode target, List<AbstractInsnNode> content) {

		ListIterator<AbstractInsnNode> iterator = list.iterator();
		while (iterator.hasNext())
		{
			if (iterator.next().equals(target))
			{
				while (iterator.hasNext())
				{
					if (iterator.next() instanceof LabelNode) {
						iterator.previous(); break;
					}
					iterator.remove();
				}
				content.forEach(iterator::add);
				return true;
			}
		}//@formatter:on
		return false;
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
