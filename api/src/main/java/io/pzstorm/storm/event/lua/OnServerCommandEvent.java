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

import se.krka.kahlua.vm.KahluaTable;

/**
 * Triggered when client receives server command.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnServerCommandEvent implements LuaEvent {

	// TODO: finish documenting this event
	public final String var1, var2;
	public final KahluaTable var3;

	public OnServerCommandEvent(String var1, String var2, KahluaTable var3) {

		this.var1 = var1;
		this.var2 = var2;
		this.var3 = var3;
	}
}
