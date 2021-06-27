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

import com.google.common.collect.ImmutableList;
import io.pzstorm.storm.core.StormClassTransformer;
import io.pzstorm.storm.event.OnLoadSoundBankEvent;
import io.pzstorm.storm.event.StormEventDispatcher;
import io.pzstorm.storm.util.AsmUtils;
import io.pzstorm.storm.util.StormUtils;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

@SuppressWarnings("SpellCheckingInspection")
public class OnLoadSoundBankHook implements StormHook {

	@Override
	public void installHook(StormClassTransformer transformer) {

		// public static long FMOD_Studio_System_LoadBankFile(String)
		InsnList loadBankFileMethod = transformer.getInstructionsForMethod(
				"FMOD_Studio_System_LoadBankFile", "(Ljava/lang/String;)J"
		);
		String eventDescriptor = StormUtils.getClassAsPath(OnLoadSoundBankEvent.class);

		// clear the method before rewriting it
		loadBankFileMethod.clear();

		loadBankFileMethod.add(AsmUtils.createInsnList(
				new LabelNode(),
				new TypeInsnNode(Opcodes.NEW, "java/lang/StringBuffer"),
				new InsnNode(Opcodes.DUP),
				new VarInsnNode(Opcodes.ALOAD, 0),
				new MethodInsnNode(Opcodes.INVOKESPECIAL, "java/lang/StringBuffer",
						"<init>", "(Ljava/lang/String;)V"),
				new VarInsnNode(Opcodes.ASTORE, 1)
		));
		// new OnLoadSoundBankEvent()
		loadBankFileMethod.add(StormEventDispatcher.callDispatchEvent(ImmutableList.of(
				new TypeInsnNode(Opcodes.NEW, eventDescriptor),
				new InsnNode(Opcodes.DUP), new VarInsnNode(Opcodes.ALOAD, 1),
				new MethodInsnNode(Opcodes.INVOKESPECIAL, eventDescriptor,
						"<init>", "(Ljava/lang/StringBuffer;)V")
		)));
		loadBankFileMethod.add(AsmUtils.createInsnList(
				new LabelNode(),
				new VarInsnNode(Opcodes.ALOAD, 1),
				new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuffer",
						"toString", "()Ljava/lang/String;"),
				new MethodInsnNode(Opcodes.INVOKESTATIC, "fmod/javafmodJNI",
						"FMOD_Studio_LoadBankFile", "(Ljava/lang/String;)J"),
				new InsnNode(Opcodes.LRETURN)
		));
	}
}
