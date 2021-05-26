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
 * Triggered when {@link IsoGameCharacter} earns skill experience.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class AddXPEvent implements LuaEvent {

	/**
	 * Game character earning experience.
	 */
	public final IsoGameCharacter player;

	/**
	 * Perk to earn experience for.
	 */
	public final PerkFactory.Perks perk;

	/**
	 * Amount of experience to earn.
	 */
	public final Float xpAmount;

	public AddXPEvent(IsoGameCharacter player, PerkFactory.Perks perk, Float xpAmount) {

		this.player = player;
		this.perk = perk;
		this.xpAmount = xpAmount;
	}
}
