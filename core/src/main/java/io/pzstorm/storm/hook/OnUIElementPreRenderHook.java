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
import io.pzstorm.storm.event.OnUIElementPreRenderEvent;
import io.pzstorm.storm.event.StormEventDispatcher;
import io.pzstorm.storm.util.AsmUtils;
import io.pzstorm.storm.util.StormUtils;

public class OnUIElementPreRenderHook implements StormHook {

	@Override
	public void installHook(StormClassTransformer transformer) {

		// public void render()
		InsnList instructions = transformer.getInstructionsForMethod("render", "()V");
		String eventDescriptor = StormUtils.getClassAsPath(OnUIElementPreRenderEvent.class);

		// new OnUIElementPreRenderEvent(UIElement)
		InsnList dispatchOnUIElementPreRenderEvent = StormEventDispatcher.callDispatchEvent(ImmutableList.of(
				new TypeInsnNode(Opcodes.NEW, eventDescriptor),
				new InsnNode(Opcodes.DUP), new VarInsnNode(Opcodes.ALOAD, 0),
				new MethodInsnNode(Opcodes.INVOKESPECIAL,
						eventDescriptor, "<init>", "(Lzombie/ui/UIElement;)V")
		));
		// ...
		// LuaManager.caller.pcallvoid(UIManager.getDefaultThread(), this.getTable().rawget("prerender"), this.table);
		// -> insert <-
		// ...
		LabelNode callPrerender = AsmUtils.getFirstMatchingLabelNode(instructions, ImmutableList.of(
				new FieldInsnNode(Opcodes.GETSTATIC, "zombie/Lua/LuaManager",
						"caller", "Lse/krka/kahlua/integration/LuaCaller;"
				),
				new MethodInsnNode(Opcodes.INVOKESTATIC, "zombie/ui/UIManager",
						"getDefaultThread", "()Lse/krka/kahlua/vm/KahluaThread;"
				),
				new VarInsnNode(Opcodes.ALOAD, 0),
				new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "zombie/ui/UIElement",
						"getTable", "()Lse/krka/kahlua/vm/KahluaTable;"
				),
				new LdcInsnNode("prerender"),
				new MethodInsnNode(Opcodes.INVOKEINTERFACE, "se/krka/kahlua/vm/KahluaTable",
						"rawget", "(Ljava/lang/Object;)Ljava/lang/Object;", true
				),
				new VarInsnNode(Opcodes.ALOAD, 0),
				new FieldInsnNode(Opcodes.GETFIELD, "zombie/ui/UIElement",
						"table", "Lse/krka/kahlua/vm/KahluaTable;"
				),
				new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "se/krka/kahlua/integration/LuaCaller", "pcallvoid",
						"(Lse/krka/kahlua/vm/KahluaThread;Ljava/lang/Object;Ljava/lang/Object;)V"))
		);
		instructions.insert(callPrerender, dispatchOnUIElementPreRenderEvent);
	}
}
