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

package io.pzstorm.storm.event.lua;

import io.pzstorm.storm.event.ZomboidEvent;
import se.krka.kahlua.luaj.compiler.LuaCompiler;
import zombie.Lua.Event;
import zombie.Lua.LuaEventManager;
import zombie.Lua.LuaManager;

import java.io.IOException;

/**
 * <p>This class represents an in-game event dispatched to Lua mods. Lua events are handled by
 * {@link LuaEventManager}. Storm intercepts these events and dispatches them as {@link LuaEvent}
 * implementation instances. Subscribing to events being defined as Java classes offers much
 * easier and safer interaction to subscribers then when subscribing from Lua.</p>
 * <h3>Note for developers</h3>
 * <p>When writing new event implementation classes do not forget to add the class
 * references to {@code LuaEventFactory} static block so that the references are
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

	default void registerCallback() {

		Event event = LuaEventManager.AddEvent(getName());
		if (event.callbacks.size() == 0)
		{

			try
			{
				event.callbacks.add(LuaCompiler.loadstring("", "console", LuaManager.env));
			}
			catch (IOException e)
			{
				throw new RuntimeException(e);
			}
		}
	}
}
