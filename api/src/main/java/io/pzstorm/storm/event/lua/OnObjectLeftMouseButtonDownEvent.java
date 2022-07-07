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

import zombie.iso.IsoObject;

/**
 * Triggered when left mouse button clicked on {@link IsoObject}.
 *
 * @see OnObjectLeftMouseButtonUpEvent
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnObjectLeftMouseButtonDownEvent implements LuaEvent {

	/**
	 * Object clicked on.
	 */
	public final IsoObject object;

	/**
	 * Position of mouse along x-axis.
	 */
	public final Double x;

	/**
	 * Position of mouse along y-axis.
	 */
	public final Double y;

	public OnObjectLeftMouseButtonDownEvent(IsoObject object, Double x, Double y) {

		this.object = object;
		this.x = x;
		this.y = y;
	}
}
