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
 * Triggers when an ambient sound is constructed.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnAmbientSoundEvent implements LuaEvent {

	/**
	 * Name of the ambient sound being constructed.
	 */
	public final String soundName;

	/**
	 * Position of ambient sound along x-axis.
	 */
	public final Float x;

	/**
	 * Position of ambient sound along y-axis.
	 */
	public final Float y;

	public OnAmbientSoundEvent(String soundName, Float x, Float y) {

		this.soundName = soundName;
		this.x = x;
		this.y = y;
	}
}
