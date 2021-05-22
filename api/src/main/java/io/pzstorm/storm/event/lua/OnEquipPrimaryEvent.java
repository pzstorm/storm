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
import zombie.inventory.InventoryItem;

/**
 * Triggered when a character equips an item in its primary slot.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnEquipPrimaryEvent implements LuaEvent {

	/**
	 * The character which equipped the item.
	 */
	public final IsoGameCharacter player;

	/**
	 * The item which was equipped.
	 */
	public final InventoryItem item;

	public OnEquipPrimaryEvent(IsoGameCharacter player, InventoryItem item) {

		this.player = player;
		this.item = item;
	}
}
