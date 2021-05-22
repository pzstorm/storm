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

import zombie.iso.IsoWorld;
import zombie.radio.ZomboidRadio;
import zombie.radio.scripting.RadioScriptManager;

/**
 * Triggered after radio scripts have been loaded in {@link ZomboidRadio}.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnLoadRadioScriptsEvent implements LuaEvent {

	/**
	 * Script manager that loaded the scripts.
	 */
	public final RadioScriptManager scriptManager;

	/**
	 * {@code true} if {@link IsoWorld#SavedWorldVersion} is {@code -1}, {@code false} otherwise.
	 */
	public final Boolean noSaveWorldVersion;

	public OnLoadRadioScriptsEvent(RadioScriptManager scriptManager, Boolean noSaveWorldVersion) {

		this.scriptManager = scriptManager;
		this.noSaveWorldVersion = noSaveWorldVersion;
	}
}
