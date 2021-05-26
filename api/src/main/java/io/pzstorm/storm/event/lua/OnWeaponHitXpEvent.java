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
import zombie.iso.IsoMovingObject;

/**
 * Triggered when experience is earned by attacking with weapon.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnWeaponHitXpEvent implements LuaEvent {

	/**
	 * Player earning experience.
	 */
	public final IsoGameCharacter player;

	/**
	 * Weapon used to attack.
	 */
	public final HandWeapon weapon;

	/**
	 * Object being hit with weapon.
	 */
	public final IsoMovingObject target;

	/**
	 * Either amount of damage or experience earned.
	 */
	public final Float value;

	public OnWeaponHitXpEvent(IsoGameCharacter player, HandWeapon weapon, IsoMovingObject target, Float value) {

		this.player = player;
		this.weapon = weapon;
		this.target = target;
		this.value = value;
	}
}
