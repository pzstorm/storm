package io.pzstorm.storm.event;

import io.pzstorm.storm.event.lua.LuaEvent;
import org.jetbrains.annotations.Unmodifiable;
import zombie.Lua.Event;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This event fires when an in-game Lua event has been triggered. This event is only used
 * internally to catch <i>most</i>  triggered Lua events, after which they are used to
 * dispatched {@link LuaEvent} instances. For performance and usability reasons it is
 * recommended to subscribe to specific {@link LuaEvent LuaEvents} rather then this event.
 */
@SuppressWarnings("WeakerAccess")
public class OnTriggerLuaEvent implements ZomboidEvent {

	public final Event luaEvent;
	public final @Unmodifiable List<Object> args;

	public OnTriggerLuaEvent(Event luaEvent, Object... args) {

		this.luaEvent = luaEvent;
		this.args = Collections.unmodifiableList(Arrays.asList(args));
	}

	@Override
	public String getName() {
		return "onTriggerLuaEvent";
	}
}
