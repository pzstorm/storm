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
import zombie.iso.IsoGridSquare;

@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnDoTileBuilding2Event implements LuaEvent {

	// TODO: document this event
	public final IsoGridSquare gridSquare;
	public final KahluaTable table;
	public final Boolean render;
	public final Integer x, y, z;

	public OnDoTileBuilding2Event(KahluaTable table, Boolean render, Integer x,
								  Integer y, Integer z, IsoGridSquare gridSquare) {

		this.gridSquare = gridSquare;
		this.table = table;
		this.render = render;
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
