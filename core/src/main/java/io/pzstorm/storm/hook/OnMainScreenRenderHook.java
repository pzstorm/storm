package io.pzstorm.storm.hook;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import com.google.common.collect.ImmutableList;

import io.pzstorm.storm.core.StormClassTransformer;
import io.pzstorm.storm.event.OnMainScreenRenderEvent;
import io.pzstorm.storm.event.StormEventDispatcher;
import io.pzstorm.storm.util.AsmUtils;
import io.pzstorm.storm.util.StormUtils;

public class OnMainScreenRenderHook implements StormHook {

	@Override
	public void installHook(StormClassTransformer transformer) {

		// public void render()
		InsnList instructions = transformer.getInstructionsForMethod("render", "()V");
		String eventDescriptor = StormUtils.getClassAsPath(OnMainScreenRenderEvent.class);

		// new OnMainScreenRenderEvent()
		InsnList dispatchOnRenderEvent = StormEventDispatcher.callDispatchEvent(ImmutableList.of(
				new TypeInsnNode(Opcodes.NEW, eventDescriptor), new InsnNode(Opcodes.DUP),
				new MethodInsnNode(Opcodes.INVOKESPECIAL, eventDescriptor, "<init>", "()V")
		));
		// ...
		// -> insert <-
		// Core.getInstance().EndFrameUI();
		// ...
		LabelNode target = AsmUtils.getFirstMatchingLabelNode(instructions, ImmutableList.of(
				new MethodInsnNode(Opcodes.INVOKESTATIC,
						"zombie/core/Core", "getInstance", "()Lzombie/core/Core;"
				),
				// INVOKEVIRTUAL zombie/core/Core.EndFrameUI ()V
				new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
						"zombie/core/Core", "EndFrameUI", "()V"))
		);
		instructions.insertBefore(target, dispatchOnRenderEvent);
	}
}
