package io.pzstorm.storm.event.lua;

import io.pzstorm.storm.event.ZomboidEvent;
import zombie.Lua.LuaEventManager;

/**
 * This class represents an in-game event dispatched to Lua mods.
 * Lua events are handled by {@link LuaEventManager}. Storm intercepts these
 * events and dispatches them as {@link LuaEvent} implementation instances.
 * Subscribing to events being defined as Java classes offers much easier
 * and safer interaction to subscribers then when subscribing from Lua.
 */
public interface LuaEvent extends ZomboidEvent {

	@Override
	default String getName() {

		String className = getClass().getSimpleName();
		if (className.endsWith("Event")) {
			return className.substring(className.length() - 4);
		}
		else return className;
	}
}
