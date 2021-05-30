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

import se.krka.kahlua.vm.KahluaTable;

/**
 * <p>Triggered when a player right clicks a world item.</p>
 * Use the event to add items to context menu without mod conflicts.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnFillWorldObjectContextMenuEvent implements LuaEvent {

	/**
	 * Index of player that triggered the event.
	 */
	public final Double playerIndex;

	// TODO: finish documenting this event
	public final KahluaTable context, worldObjects;
	public final Boolean isTest;

	public OnFillWorldObjectContextMenuEvent(Double arg1, KahluaTable context, KahluaTable worldObjects, Boolean isTest) {
		this.playerIndex = arg1;
		this.context = context;
		this.worldObjects = worldObjects;
		this.isTest = isTest;
	}
}
