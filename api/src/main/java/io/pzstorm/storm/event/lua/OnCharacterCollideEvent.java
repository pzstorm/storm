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
import zombie.iso.IsoMovingObject;
import zombie.iso.IsoObject;

/**
 * Called when an object collides with {@link IsoGameCharacter}.
 *
 * @see OnObjectCollideEvent
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnCharacterCollideEvent implements LuaEvent {

	public final IsoMovingObject object;
	public final IsoObject character;

	public OnCharacterCollideEvent(IsoMovingObject object, IsoObject character) {

		this.object = object;
		this.character = character;
	}
}
