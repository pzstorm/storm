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

package io.pzstorm.storm.hook;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import com.google.common.collect.ImmutableList;

import io.pzstorm.storm.core.StormClassTransformer;
import io.pzstorm.storm.event.OnLoadSoundBanksEvent;
import io.pzstorm.storm.event.StormEventDispatcher;
import io.pzstorm.storm.util.AsmUtils;
import io.pzstorm.storm.util.StormUtils;

public class OnLoadSoundBanksHook implements StormHook {

	@Override
	public void installHook(StormClassTransformer transformer) {

		// public void init()
		InsnList instructions = transformer.getInstructionsForMethod("init", "()V");
		String eventDescriptor = StormUtils.getClassAsPath(OnLoadSoundBanksEvent.class);

		// new OnLoadSoundBanksEvent()
		InsnList dispatchOnLoadSoundBanksEvent = StormEventDispatcher.callDispatchEvent(ImmutableList.of(
				new TypeInsnNode(Opcodes.NEW, eventDescriptor), new InsnNode(Opcodes.DUP),
				new MethodInsnNode(Opcodes.INVOKESPECIAL, eventDescriptor, "<init>", "()V")
		));
		// javafmod.FMOD_System_Set3DSettings(1.0f, 1.0f, 1.0f);
		// -> insert <-
		// this.loadBank("ZomboidSound.strings");
		// ...
		LabelNode target = AsmUtils.getFirstMatchingLabelNode(instructions, ImmutableList.of(
				new VarInsnNode(Opcodes.ALOAD, 0),
				new LdcInsnNode("ZomboidSound.strings"),
				new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "fmod/fmod/FMODManager",
						"loadBank", "(Ljava/lang/String;)J")
		));
		instructions.insertBefore(target, dispatchOnLoadSoundBanksEvent);
	}
}
