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

import org.jetbrains.annotations.Nullable;

import zombie.inventory.InventoryItem;
import zombie.iso.IsoGridSquare;
import zombie.iso.IsoObject;

/**
 * Triggered when an inventory container is being updated.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnContainerUpdateEvent implements LuaEvent {

	/**
	 * Position of the inventory container being updated.
	 */
	public final @Nullable IsoGridSquare gridSquare;

	/**
	 * Item that triggered the container update.
	 */
	public final @Nullable InventoryItem item;

	/**
	 * Object that triggered the container update.
	 */
	public final @Nullable IsoObject object;

	private OnContainerUpdateEvent(@Nullable IsoGridSquare gridSquare,
								   @Nullable InventoryItem item, @Nullable IsoObject object) {

		this.gridSquare = gridSquare;
		this.item = item;
		this.object = object;
	}

	public OnContainerUpdateEvent(@Nullable IsoObject deadBody) {
		this(null, null, deadBody);
	}

	public OnContainerUpdateEvent(@Nullable InventoryItem item) {
		this(null, item, null);
	}

	public OnContainerUpdateEvent(@Nullable IsoGridSquare gridSquare) {
		this(gridSquare, null, null);
	}

	public OnContainerUpdateEvent() {
		this(null, null, null);
	}
}
