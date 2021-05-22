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

import java.util.ArrayList;

@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnGetTableResultEvent implements LuaEvent {

	// TODO: document this event
	public final ArrayList<?> var1;
	public final Integer var2;
	public final String var3;

	public OnGetTableResultEvent(ArrayList<?> var1, Integer var2, String var3) {

		this.var1 = var1;
		this.var2 = var2;
		this.var3 = var3;
	}
}
