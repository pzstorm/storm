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

import zombie.radio.devices.WaveSignalDevice;

@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnDeviceTextEvent implements LuaEvent {

	// TODO: document this event
	public final WaveSignalDevice device;
	public final String text1, text2;
	public final Float x, y, z;

	public OnDeviceTextEvent(String text1, Integer x, Integer y, Integer z, String text2, WaveSignalDevice device) {

		this.text1 = text1;
		this.x = (float) x;
		this.y = (float) y;
		this.z = (float) z;
		this.text2 = text2;
		this.device = device;
	}

	public OnDeviceTextEvent(String text1, Float x, Float y, WaveSignalDevice device) {

		this.text1 = text1;
		this.x = x;
		this.y = y;
		this.z = -1f;
		this.text2 = "";
		this.device = device;
	}
}
