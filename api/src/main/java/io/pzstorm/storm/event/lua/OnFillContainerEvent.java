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

import zombie.inventory.ItemContainer;

/**
 * Triggered after {@link ItemContainer} has being filled.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnFillContainerEvent implements LuaEvent {

	/**
	 * Name of the room in which the container is located in.
	 */
	public final String roomName;

	/**
	 * Type of container being filled.
	 */
	public final String containerType;

	/**
	 * Instance of container being filled.
	 */
	public final ItemContainer container;

	public OnFillContainerEvent(String roomName, String containerType, ItemContainer container) {

		this.roomName = roomName;
		this.containerType = containerType;
		this.container = container;
	}
}
