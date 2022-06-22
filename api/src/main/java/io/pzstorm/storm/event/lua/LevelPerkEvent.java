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
import zombie.characters.skills.PerkFactory;

/**
 * Triggered when a perk is leveled up or down.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class LevelPerkEvent implements LuaEvent {

	/**
	 * The character whose perk is being leveled up or down.
	 */
	public final IsoGameCharacter player;

	/**
	 * The perk being leveled up.
	 */
	public final PerkFactory.Perk perk;

	/**
	 * Perk level after leveling up or down.
	 */
	public final Integer level;

	/**
	 * {@code true} if leveling perk up or {@code false} if leveling down.
	 */
	public final Boolean levelingUp;

	public LevelPerkEvent(IsoGameCharacter player, PerkFactory.Perk perk, Integer level, Boolean levelingUp) {

		this.player = player;
		this.perk = perk;
		this.level = level;
		this.levelingUp = levelingUp;
	}
}
