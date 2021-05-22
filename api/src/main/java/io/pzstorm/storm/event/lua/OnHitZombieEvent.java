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

import zombie.characters.BodyDamage.BodyPartType;
import zombie.characters.IsoGameCharacter;
import zombie.characters.IsoZombie;
import zombie.inventory.types.HandWeapon;

/**
 * Triggered when {@link IsoZombie} was hit by {@link IsoGameCharacter}.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnHitZombieEvent implements LuaEvent {

	/**
	 * Instance of zombie being attacked.
	 */
	public final IsoZombie zombie;

	/**
	 * Instance of character attacking zombie.
	 */
	public final IsoGameCharacter attacker;

	/**
	 * Body part that has been hit.
	 */
	public final BodyPartType target;

	/**
	 * Weapon used to hit zombie.
	 */
	public final HandWeapon weapon;

	public OnHitZombieEvent(IsoZombie zombie, IsoGameCharacter attacker, BodyPartType target, HandWeapon weapon) {

		this.zombie = zombie;
		this.attacker = attacker;
		this.target = target;
		this.weapon = weapon;
	}
}
