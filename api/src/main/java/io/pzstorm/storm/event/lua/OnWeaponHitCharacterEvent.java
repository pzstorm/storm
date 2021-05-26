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
import zombie.inventory.types.HandWeapon;

/**
 * Triggered when a {@link IsoGameCharacter} has been hit by a {@link HandWeapon}.
 *
 * @see OnWeaponSwingEvent
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnWeaponHitCharacterEvent implements LuaEvent {

	public final IsoGameCharacter attacker;
	public final IsoGameCharacter target;
	public final HandWeapon weapon;
	public final Float damage;

	public OnWeaponHitCharacterEvent(IsoGameCharacter attacker, IsoGameCharacter target, HandWeapon weapon, Float damage) {

		this.attacker = attacker;
		this.target = target;
		this.weapon = weapon;
		this.damage = damage;
	}
}
