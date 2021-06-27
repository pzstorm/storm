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

import io.pzstorm.storm.event.lua.LuaEvent;

/**
 * This event triggers when a sound bank is being loaded.
 * Users can use this event to change the path of sound banks being loaded,
 * effectively replacing vanilla with their own custom sound banks.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnLoadSoundBankEvent implements LuaEvent {

	/**
	 * {@code StringBuffer} containing the string that denotes the path to the sound bank being loaded.
	 * In order to replace the given path with a custom path you can do the following:
	 * <pre>
	 * String customPath = "path/to/custom/sound/bank";
	 * soundBankPath.delete(0, soundBankPath.length()).append(customPath);
	 * </pre>
	 */
	public final StringBuffer soundBankPath;

	public OnLoadSoundBankEvent(StringBuffer soundBankPath) {
		this.soundBankPath = soundBankPath;
	}
}
