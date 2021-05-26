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

/**
 * Triggered when mouse is moved.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnMouseMoveEvent implements LuaEvent {

	// TODO: finish documenting this event
	public final Double xA;
	public final Double yA;
	public final Double x;
	public final Double y;

	public OnMouseMoveEvent(Double xA, Double yA, Double x, Double y) {

		this.xA = xA;
		this.yA = yA;
		this.x = x;
		this.y = y;
	}
}
