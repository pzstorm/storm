package io.pzstorm.storm.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableMap;

import io.pzstorm.storm.UnitTest;
import io.pzstorm.storm.event.lua.*;
import se.krka.kahlua.vm.KahluaTable;
import zombie.characters.IsoGameCharacter;
import zombie.characters.IsoPlayer;
import zombie.characters.IsoZombie;
import zombie.characters.SurvivorDesc;
import zombie.inventory.types.HandWeapon;
import zombie.iso.BuildingDef;
import zombie.iso.IsoMovingObject;
import zombie.iso.IsoObject;

class LuaEventFactoryTest implements UnitTest {

	private static final ImmutableMap<Class<? extends LuaEvent>, Class<?>[]> EVENT_CLASS_CONSTRUCTOR_DATA =
			ImmutableMap.<Class<? extends LuaEvent>, Class<?>[]>builder()
					.put(OnChallengeQueryEvent.class, new Class<?>[] {})
					.put(OnCharacterCollideEvent.class, new Class<?>[] {
							IsoMovingObject.class, IsoObject.class
					})
					.put(OnCreateLivingCharacterEvent.class, new Class<?>[] {
							IsoPlayer.class, SurvivorDesc.class
					})
					.put(OnFETickEvent.class, new Class<?>[] {
							Integer.class
					})
					.put(OnFillInventoryObjectContextMenuEvent.class, new Class<?>[] {
							Double.class, KahluaTable.class, KahluaTable.class
					})
					.put(OnFillWorldObjectContextMenuEvent.class, new Class<?>[] {
							Double.class, KahluaTable.class, KahluaTable.class, Boolean.class
					})
					.put(OnGameBootEvent.class, new Class<?>[] {})
					.put(OnGameStartEvent.class, new Class<?>[] {})
					.put(OnJoypadActivateEvent.class, new Class<?>[] {
							Integer.class
					})
					.put(OnKeyPressedEvent.class, new Class<?>[] {
							Integer.class
					})
					.put(OnKeyStartPressedEvent.class, new Class<?>[] {
							Integer.class
					})
					.put(OnLoadMapZonesEvent.class, new Class<?>[] {})
					.put(OnMultiTriggerNPCEvent.class, new Class<?>[] {
							String.class, KahluaTable.class, BuildingDef.class
					})
					.put(OnObjectCollideEvent.class, new Class<?>[] {
							IsoMovingObject.class, IsoObject.class
					})
					.put(OnPlayerUpdateEvent.class, new Class<?>[] {
							IsoPlayer.class
					})
					.put(OnPostUIDrawEvent.class, new Class<?>[] {})
					.put(OnPreFillInventoryObjectContextMenuEvent.class, new Class<?>[] {
							Double.class, KahluaTable.class, KahluaTable.class
					})
					.put(OnPreUIDrawEvent.class, new Class<?>[] {})
					.put(OnRefreshInventoryWindowContainersEvent.class, new Class<?>[] {
							KahluaTable.class, String.class
					})
					.put(OnTickEvenPausedEvent.class, new Class<?>[] {
							Double.class
					})
					.put(OnTickEvent.class, new Class<?>[] {
							Double.class
					})
					.put(OnTriggerNPCEvent.class, new Class<?>[] {
							String.class, KahluaTable.class, BuildingDef.class
					})
					.put(OnWeaponHitCharacterEvent.class, new Class<?>[] {
							IsoGameCharacter.class, IsoGameCharacter.class, HandWeapon.class, Float.class
					})
					.put(OnWeaponSwingEvent.class, new Class<?>[] {
							IsoGameCharacter.class, HandWeapon.class
					})
					.put(OnZombieUpdateEvent.class, new Class<?>[] {
							IsoZombie.class
					})
					.build();

	@Test
	void shouldConstructLuaEventFromClassInstance() {

		for (Map.Entry<Class<? extends LuaEvent>, Class<?>[]> entry : EVENT_CLASS_CONSTRUCTOR_DATA.entrySet())
		{
			Class<? extends LuaEvent> eventClass = entry.getKey();
			List<Object> constructorArgs = getDummyConstructorArgList(entry.getValue());

			LuaEvent event = LuaEventFactory.constructLuaEvent(eventClass, constructorArgs.toArray());
			Assertions.assertTrue(eventClass.isAssignableFrom(event.getClass()));
		}
	}

	@Test
	void shouldConstructLuaEventFromClassName() {

		for (Map.Entry<Class<? extends LuaEvent>, Class<?>[]> entry : EVENT_CLASS_CONSTRUCTOR_DATA.entrySet())
		{
			Class<? extends LuaEvent> eventClass = entry.getKey();
			List<Object> constructorArgs = getDummyConstructorArgList(entry.getValue());
			String eventName = LuaEventFactory.getEventName(eventClass);

			LuaEvent event = LuaEventFactory.constructLuaEvent(eventName, constructorArgs.toArray());
			Assertions.assertTrue(eventClass.isAssignableFrom(event.getClass()));
		}
	}

	@Test
	void shouldConstructLuaEventWithMultipleConstructors() {

		LuaEvent event = LuaEventFactory.constructLuaEvent(OnConnectFailedEvent.class, "");
		Assertions.assertTrue(OnConnectFailedEvent.class.isAssignableFrom(event.getClass()));

		event = LuaEventFactory.constructLuaEvent(OnConnectFailedEvent.class);
		Assertions.assertTrue(OnConnectFailedEvent.class.isAssignableFrom(event.getClass()));
	}

	private List<Object> getDummyConstructorArgList(Class<?>[] constructorArgTypes) {

		List<Object> result = new ArrayList<>();
		for (Class<?> argType : constructorArgTypes)
		{
			Object object;
			if (argType.isPrimitive())
			{
				if (argType == int.class) {
					object = 0;
				}
				else if (argType == double.class) {
					object = 0.0D;
				}
				else if (argType == float.class) {
					object = 0.0f;
				}
				else if (argType == short.class) {
					object = (short) 0;
				}
				else if (argType == long.class) {
					object = (long) 0;
				}
				else if (argType == byte.class) {
					object = (byte) 0;
				}
				else if (argType == char.class) {
					object = 'a';
				}
				else if (argType == boolean.class) {
					object = false;
				}
				else throw new UnsupportedOperationException();
			}
			else object = argType.cast(null);
			result.add(object);
		}
		return result;
	}
}
