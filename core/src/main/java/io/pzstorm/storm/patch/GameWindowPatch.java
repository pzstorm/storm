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

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LdcInsnNode;

import io.pzstorm.storm.core.StormClassTransformer;

public class GameWindowPatch implements ZomboidPatch {

	@Override
	public void applyPatch(StormClassTransformer transformer) {

		InsnList initMethodInsn = transformer.getInstructionsForMethod("init", "()V");
		for (AbstractInsnNode instruction : initMethodInsn)
		{
			// replace OnLoadSoundBanks with OnGameWindowInit event
			if (instruction instanceof LdcInsnNode)
			{
				LdcInsnNode ldcNode = ((LdcInsnNode) instruction);
				if (ldcNode.cst.equals("OnLoadSoundBanks")) {
					ldcNode.cst = "OnGameWindowInit";
				}
			}
		}
	}
}
