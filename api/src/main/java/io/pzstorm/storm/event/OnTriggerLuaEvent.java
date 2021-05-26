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

package io.pzstorm.storm.event;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.Unmodifiable;

import io.pzstorm.storm.event.lua.LuaEvent;
import zombie.Lua.Event;

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
