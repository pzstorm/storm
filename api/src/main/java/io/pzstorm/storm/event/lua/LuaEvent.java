package io.pzstorm.storm.event.lua;

import io.pzstorm.storm.event.ZomboidEvent;
import zombie.Lua.LuaEventManager;

/**
 * <p>This class represents an in-game event dispatched to Lua mods. Lua events are handled by
 * {@link LuaEventManager}. Storm intercepts these events and dispatches them as {@link LuaEvent}
 * implementation instances. Subscribing to events being defined as Java classes offers much
 * easier and safer interaction to subscribers then when subscribing from Lua.</p>
 * <h3>Note for developers</h3>
 * <p>When writing new event implementation classes do not forget to add the class
 * references to {@link LuaEventFactory} static block so that the references are
 * properly mapped and recognized by factory. If the references are not added the
 * factory will not be able create new instances of those event.</p>
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
