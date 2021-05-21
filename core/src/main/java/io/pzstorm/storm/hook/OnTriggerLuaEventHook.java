package io.pzstorm.storm.hook;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import com.google.common.collect.ImmutableList;

import io.pzstorm.storm.core.StormClassTransformer;
import io.pzstorm.storm.event.OnTriggerLuaEvent;
import io.pzstorm.storm.event.StormEventDispatcher;
import io.pzstorm.storm.util.AsmUtils;
import io.pzstorm.storm.util.StormUtils;

public class OnTriggerLuaEventHook implements StormHook {

	@Override
	public void installHook(StormClassTransformer transformer) {
		String eventDescriptor = StormUtils.getClassAsPath(OnTriggerLuaEvent.class);

		// new OnTriggerLuaEvent(Event)
		InsnList onTriggerLuaEvent1 = StormEventDispatcher.callDispatchEvent(ImmutableList.of(
				new TypeInsnNode(Opcodes.NEW, eventDescriptor),
				new InsnNode(Opcodes.DUP),
				new VarInsnNode(Opcodes.ALOAD, 2),
				new InsnNode(Opcodes.ICONST_0),
				new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/Object"),
				new MethodInsnNode(Opcodes.INVOKESPECIAL, eventDescriptor, "<init>",
						"(Lzombie/Lua/Event;[Ljava/lang/Object;)V")
		));
		// public static void triggerEvent(String)
		InsnList triggerEvent1 = transformer.getInstructionsForMethod("triggerEvent",
				"(Ljava/lang/String;)V"
		);
		LabelNode target1 = AsmUtils.getFirstMatchingLabelNode(triggerEvent1, ImmutableList.of(
				new VarInsnNode(Opcodes.ALOAD, 2),
				new FieldInsnNode(Opcodes.GETSTATIC, "zombie/Lua/LuaManager", "env",
						"Lse/krka/kahlua/vm/KahluaTable;"),
				new FieldInsnNode(Opcodes.GETSTATIC, "zombie/Lua/LuaManager", "caller",
						"Lse/krka/kahlua/integration/LuaCaller;"),
				new InsnNode(Opcodes.ACONST_NULL),
				new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "zombie/Lua/Event", "trigger",
						"(Lse/krka/kahlua/vm/KahluaTable;" +
								"Lse/krka/kahlua/integration/LuaCaller;[Ljava/lang/Object;)Z"))
		);
		triggerEvent1.insertBefore(target1, onTriggerLuaEvent1);

		// new OnTriggerLuaEvent(Event,Object)
		InsnList onTriggerLuaEvent2 = StormEventDispatcher.callDispatchEvent(ImmutableList.of(
				new TypeInsnNode(Opcodes.NEW, eventDescriptor),
				new InsnNode(Opcodes.DUP),
				new VarInsnNode(Opcodes.ALOAD, 3),
				new InsnNode(Opcodes.ICONST_1),
				new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/Object"),
				new InsnNode(Opcodes.DUP),
				new InsnNode(Opcodes.ICONST_0),
				new VarInsnNode(Opcodes.ALOAD, 1),
				new InsnNode(Opcodes.AASTORE),
				new MethodInsnNode(Opcodes.INVOKESPECIAL, eventDescriptor, "<init>",
						"(Lzombie/Lua/Event;[Ljava/lang/Object;)V")
		));
		// public static void triggerEvent(String,Object)
		InsnList triggerEvent2 = transformer.getInstructionsForMethod("triggerEvent",
				"(Ljava/lang/String;Ljava/lang/Object;)V"
		);
		LabelNode trigger2 = AsmUtils.getFirstMatchingLabelNode(triggerEvent2, ImmutableList.of(
				new VarInsnNode(Opcodes.ALOAD, 3),
				new FieldInsnNode(Opcodes.GETSTATIC, "zombie/Lua/LuaManager", "env",
						"Lse/krka/kahlua/vm/KahluaTable;"),
				new FieldInsnNode(Opcodes.GETSTATIC, "zombie/Lua/LuaManager", "caller",
						"Lse/krka/kahlua/integration/LuaCaller;"),
				new VarInsnNode(Opcodes.ALOAD, 4),
				new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "zombie/Lua/Event", "trigger",
						"(Lse/krka/kahlua/vm/KahluaTable;" +
								"Lse/krka/kahlua/integration/LuaCaller;[Ljava/lang/Object;)Z"))
		);
		// this operation will expand MAXSTACK by 3
		triggerEvent2.insertBefore(trigger2, onTriggerLuaEvent2);

		// new OnTriggerLuaEvent(Event,Object,Object)
		InsnList onTriggerLuaEvent3 = StormEventDispatcher.callDispatchEvent(ImmutableList.of(
				new TypeInsnNode(Opcodes.NEW, eventDescriptor),
				new InsnNode(Opcodes.DUP),
				new VarInsnNode(Opcodes.ALOAD, 4),
				new InsnNode(Opcodes.ICONST_2),
				new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/Object"),
				new InsnNode(Opcodes.DUP),
				new InsnNode(Opcodes.ICONST_0),
				new VarInsnNode(Opcodes.ALOAD, 1),
				new InsnNode(Opcodes.AASTORE),
				new InsnNode(Opcodes.DUP),
				new InsnNode(Opcodes.ICONST_1),
				new VarInsnNode(Opcodes.ALOAD, 2),
				new InsnNode(Opcodes.AASTORE),
				new MethodInsnNode(Opcodes.INVOKESPECIAL, eventDescriptor, "<init>",
						"(Lzombie/Lua/Event;[Ljava/lang/Object;)V")
		));
		// public static void triggerEvent(String,Object,Object)
		InsnList triggerEvent3 = transformer.getInstructionsForMethod("triggerEvent",
				"(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V"
		);
		LabelNode trigger3 = AsmUtils.getFirstMatchingLabelNode(triggerEvent3, ImmutableList.of(
				new VarInsnNode(Opcodes.ALOAD, 4),
				new FieldInsnNode(Opcodes.GETSTATIC, "zombie/Lua/LuaManager", "env",
						"Lse/krka/kahlua/vm/KahluaTable;"),
				new FieldInsnNode(Opcodes.GETSTATIC, "zombie/Lua/LuaManager", "caller",
						"Lse/krka/kahlua/integration/LuaCaller;"),
				new VarInsnNode(Opcodes.ALOAD, 5),
				new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "zombie/Lua/Event", "trigger",
						"(Lse/krka/kahlua/vm/KahluaTable;" +
								"Lse/krka/kahlua/integration/LuaCaller;[Ljava/lang/Object;)Z"))
		);
		// this operation will expand MAXSTACK by 3
		triggerEvent3.insertBefore(trigger3, onTriggerLuaEvent3);

		// new OnTriggerLuaEvent(Event,Object,Object,Object)
		InsnList onTriggerLuaEvent4 = StormEventDispatcher.callDispatchEvent(ImmutableList.of(
				new TypeInsnNode(Opcodes.NEW, eventDescriptor),
				new InsnNode(Opcodes.DUP),
				new VarInsnNode(Opcodes.ALOAD, 5),
				new InsnNode(Opcodes.ICONST_3),
				new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/Object"),
				new InsnNode(Opcodes.DUP),
				new InsnNode(Opcodes.ICONST_0),
				new VarInsnNode(Opcodes.ALOAD, 1),
				new InsnNode(Opcodes.AASTORE),
				new InsnNode(Opcodes.DUP),
				new InsnNode(Opcodes.ICONST_1),
				new VarInsnNode(Opcodes.ALOAD, 2),
				new InsnNode(Opcodes.AASTORE),
				new InsnNode(Opcodes.DUP),
				new InsnNode(Opcodes.ICONST_2),
				new VarInsnNode(Opcodes.ALOAD, 3),
				new InsnNode(Opcodes.AASTORE),
				new MethodInsnNode(Opcodes.INVOKESPECIAL, eventDescriptor, "<init>",
						"(Lzombie/Lua/Event;[Ljava/lang/Object;)V")
		));
		// public static void triggerEvent(String,Object,Object,Object)
		InsnList triggerEvent4 = transformer.getInstructionsForMethod("triggerEvent",
				"(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V"
		);
		LabelNode trigger4 = AsmUtils.getFirstMatchingLabelNode(triggerEvent4, ImmutableList.of(
				new VarInsnNode(Opcodes.ALOAD, 5),
				new FieldInsnNode(Opcodes.GETSTATIC, "zombie/Lua/LuaManager", "env",
						"Lse/krka/kahlua/vm/KahluaTable;"),
				new FieldInsnNode(Opcodes.GETSTATIC, "zombie/Lua/LuaManager", "caller",
						"Lse/krka/kahlua/integration/LuaCaller;"),
				new VarInsnNode(Opcodes.ALOAD, 6),
				new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "zombie/Lua/Event", "trigger",
						"(Lse/krka/kahlua/vm/KahluaTable;" +
								"Lse/krka/kahlua/integration/LuaCaller;[Ljava/lang/Object;)Z"))
		);
		// this operation will expand MAXSTACK by 3
		triggerEvent4.insertBefore(trigger4, onTriggerLuaEvent4);

		// new OnTriggerLuaEvent(Event,Object,Object,Object,Object)
		InsnList onTriggerLuaEvent5 = StormEventDispatcher.callDispatchEvent(ImmutableList.of(
				new TypeInsnNode(Opcodes.NEW, eventDescriptor),
				new InsnNode(Opcodes.DUP),
				new VarInsnNode(Opcodes.ALOAD, 6),
				new InsnNode(Opcodes.ICONST_4),
				new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/Object"),
				new InsnNode(Opcodes.DUP),
				new InsnNode(Opcodes.ICONST_0),
				new VarInsnNode(Opcodes.ALOAD, 1),
				new InsnNode(Opcodes.AASTORE),
				new InsnNode(Opcodes.DUP),
				new InsnNode(Opcodes.ICONST_1),
				new VarInsnNode(Opcodes.ALOAD, 2),
				new InsnNode(Opcodes.AASTORE),
				new InsnNode(Opcodes.DUP),
				new InsnNode(Opcodes.ICONST_2),
				new VarInsnNode(Opcodes.ALOAD, 3),
				new InsnNode(Opcodes.AASTORE),
				new InsnNode(Opcodes.DUP),
				new InsnNode(Opcodes.ICONST_3),
				new VarInsnNode(Opcodes.ALOAD, 4),
				new InsnNode(Opcodes.AASTORE),
				new MethodInsnNode(Opcodes.INVOKESPECIAL, eventDescriptor, "<init>",
						"(Lzombie/Lua/Event;[Ljava/lang/Object;)V")
		));
		// public static void triggerEvent(String,Object,Object,Object,Object)
		InsnList triggerEvent5 = transformer.getInstructionsForMethod("triggerEvent",
				"(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;" +
						"Ljava/lang/Object;)V"
		);
		LabelNode trigger5 = AsmUtils.getFirstMatchingLabelNode(triggerEvent5, ImmutableList.of(
				new VarInsnNode(Opcodes.ALOAD, 6),
				new FieldInsnNode(Opcodes.GETSTATIC, "zombie/Lua/LuaManager", "env",
						"Lse/krka/kahlua/vm/KahluaTable;"),
				new FieldInsnNode(Opcodes.GETSTATIC, "zombie/Lua/LuaManager", "caller",
						"Lse/krka/kahlua/integration/LuaCaller;"),
				new VarInsnNode(Opcodes.ALOAD, 7),
				new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "zombie/Lua/Event", "trigger",
						"(Lse/krka/kahlua/vm/KahluaTable;" +
								"Lse/krka/kahlua/integration/LuaCaller;[Ljava/lang/Object;)Z"))
		);
		// this operation will expand MAXSTACK by 3
		triggerEvent5.insertBefore(trigger5, onTriggerLuaEvent5);

		// new OnTriggerLuaEvent(Event,Object,Object,Object,Object,Object)
		InsnList onTriggerLuaEvent6 = StormEventDispatcher.callDispatchEvent(ImmutableList.of(
				new TypeInsnNode(Opcodes.NEW, eventDescriptor),
				new InsnNode(Opcodes.DUP),
				new VarInsnNode(Opcodes.ALOAD, 7),
				new InsnNode(Opcodes.ICONST_4),
				new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/Object"),
				new InsnNode(Opcodes.DUP),
				new InsnNode(Opcodes.ICONST_0),
				new VarInsnNode(Opcodes.ALOAD, 1),
				new InsnNode(Opcodes.AASTORE),
				new InsnNode(Opcodes.DUP),
				new InsnNode(Opcodes.ICONST_1),
				new VarInsnNode(Opcodes.ALOAD, 2),
				new InsnNode(Opcodes.AASTORE),
				new InsnNode(Opcodes.DUP),
				new InsnNode(Opcodes.ICONST_2),
				new VarInsnNode(Opcodes.ALOAD, 3),
				new InsnNode(Opcodes.AASTORE),
				new InsnNode(Opcodes.DUP),
				new InsnNode(Opcodes.ICONST_3),
				new VarInsnNode(Opcodes.ALOAD, 4),
				new InsnNode(Opcodes.AASTORE),
				new InsnNode(Opcodes.DUP),
				new InsnNode(Opcodes.ICONST_3),
				new VarInsnNode(Opcodes.ALOAD, 5),
				new InsnNode(Opcodes.AASTORE),
				new MethodInsnNode(Opcodes.INVOKESPECIAL, eventDescriptor, "<init>",
						"(Lzombie/Lua/Event;[Ljava/lang/Object;)V")
		));
		// public static void triggerEvent(String,Object,Object,Object,Object,Object)
		InsnList triggerEvent6 = transformer.getInstructionsForMethod("triggerEvent",
				"(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;" +
						"Ljava/lang/Object;Ljava/lang/Object;)V"
		);
		LabelNode trigger6 = AsmUtils.getFirstMatchingLabelNode(triggerEvent6, ImmutableList.of(
				new VarInsnNode(Opcodes.ALOAD, 7),
				new FieldInsnNode(Opcodes.GETSTATIC, "zombie/Lua/LuaManager", "env",
						"Lse/krka/kahlua/vm/KahluaTable;"),
				new FieldInsnNode(Opcodes.GETSTATIC, "zombie/Lua/LuaManager", "caller",
						"Lse/krka/kahlua/integration/LuaCaller;"),
				new VarInsnNode(Opcodes.ALOAD, 8),
				new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "zombie/Lua/Event", "trigger",
						"(Lse/krka/kahlua/vm/KahluaTable;" +
								"Lse/krka/kahlua/integration/LuaCaller;[Ljava/lang/Object;)Z"))
		);
		// this operation will expand MAXSTACK by 3
		triggerEvent6.insertBefore(trigger6, onTriggerLuaEvent6);

		// new OnTriggerLuaEvent(Event,Object,Object,Object,Object,Object,Object)
		InsnList onTriggerLuaEvent7 = StormEventDispatcher.callDispatchEvent(ImmutableList.of(
				new TypeInsnNode(Opcodes.NEW, eventDescriptor),
				new InsnNode(Opcodes.DUP),
				new VarInsnNode(Opcodes.ALOAD, 8),
				new InsnNode(Opcodes.ICONST_4),
				new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/Object"),
				new InsnNode(Opcodes.DUP),
				new InsnNode(Opcodes.ICONST_0),
				new VarInsnNode(Opcodes.ALOAD, 1),
				new InsnNode(Opcodes.AASTORE),
				new InsnNode(Opcodes.DUP),
				new InsnNode(Opcodes.ICONST_1),
				new VarInsnNode(Opcodes.ALOAD, 2),
				new InsnNode(Opcodes.AASTORE),
				new InsnNode(Opcodes.DUP),
				new InsnNode(Opcodes.ICONST_2),
				new VarInsnNode(Opcodes.ALOAD, 3),
				new InsnNode(Opcodes.AASTORE),
				new InsnNode(Opcodes.DUP),
				new InsnNode(Opcodes.ICONST_3),
				new VarInsnNode(Opcodes.ALOAD, 4),
				new InsnNode(Opcodes.AASTORE),
				new InsnNode(Opcodes.DUP),
				new InsnNode(Opcodes.ICONST_3),
				new VarInsnNode(Opcodes.ALOAD, 5),
				new InsnNode(Opcodes.AASTORE),
				new InsnNode(Opcodes.DUP),
				new InsnNode(Opcodes.ICONST_3),
				new VarInsnNode(Opcodes.ALOAD, 6),
				new InsnNode(Opcodes.AASTORE),
				new MethodInsnNode(Opcodes.INVOKESPECIAL, eventDescriptor, "<init>",
						"(Lzombie/Lua/Event;[Ljava/lang/Object;)V")
		));
		// public static void triggerEvent(String,Object,Object,Object,Object,Object,Object)
		InsnList triggerEvent7 = transformer.getInstructionsForMethod("triggerEvent",
				"(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;" +
						"Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V"
		);
		LabelNode trigger7 = AsmUtils.getFirstMatchingLabelNode(triggerEvent7, ImmutableList.of(
				new VarInsnNode(Opcodes.ALOAD, 8),
				new FieldInsnNode(Opcodes.GETSTATIC, "zombie/Lua/LuaManager", "env",
						"Lse/krka/kahlua/vm/KahluaTable;"),
				new FieldInsnNode(Opcodes.GETSTATIC, "zombie/Lua/LuaManager", "caller",
						"Lse/krka/kahlua/integration/LuaCaller;"),
				new VarInsnNode(Opcodes.ALOAD, 9),
				new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "zombie/Lua/Event", "trigger",
						"(Lse/krka/kahlua/vm/KahluaTable;" +
								"Lse/krka/kahlua/integration/LuaCaller;[Ljava/lang/Object;)Z"))
		);
		// this operation will expand MAXSTACK by 3
		triggerEvent7.insertBefore(trigger7, onTriggerLuaEvent7);
	}
}
