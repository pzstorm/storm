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

import zombie.characters.IsoGameCharacter;
import zombie.characters.IsoPlayer;
import zombie.characters.IsoSurvivor;
import zombie.characters.SurvivorDesc;

/**
 * Called when {@link IsoPlayer} or {@link IsoSurvivor} is being created.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnCreateLivingCharacterEvent implements LuaEvent {

	public final IsoGameCharacter character;
	public final SurvivorDesc descriptor;

	public OnCreateLivingCharacterEvent(IsoGameCharacter character, SurvivorDesc descriptor) {

		this.character = character;
		this.descriptor = descriptor;
	}
}
