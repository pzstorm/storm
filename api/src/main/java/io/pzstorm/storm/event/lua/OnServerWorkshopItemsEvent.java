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

import java.util.ArrayList;

/**
 * Triggers when Steam workshop item is being processed.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnServerWorkshopItemsEvent implements LuaEvent {

	public final String state, errorReason;
	public final Long workshopItemID;
	public final ArrayList<String> itemIDs;
	public final Long var1, var2;

	public OnServerWorkshopItemsEvent(String state, String errorReason, Long workshopItemID,
									  ArrayList<String> itemIDs, Long var1, Long var2) {

		this.state = state;
		this.errorReason = errorReason;
		this.workshopItemID = workshopItemID;
		this.itemIDs = itemIDs;
		this.var1 = var1;
		this.var2 = var2;
	}

	public OnServerWorkshopItemsEvent(String state, String workshopItemID, Long var1, Long var2) {
		this(state, "", Long.parseLong(workshopItemID), new ArrayList<>(), var1, var2);
	}

	public OnServerWorkshopItemsEvent(String state, ArrayList<String> itemIDs) {
		this(state, "", -1L, itemIDs, -1L, -1L);
	}

	public OnServerWorkshopItemsEvent(String state, String errorReason) {
		this(state, errorReason, -1L, new ArrayList<>(), -1L, -1L);
	}

	public OnServerWorkshopItemsEvent(String state, long workshopItemID, String errorReason) {
		this(state, errorReason, workshopItemID, new ArrayList<>(), -1L, -1L);
	}

	public OnServerWorkshopItemsEvent(String state) {
		this(state, "", -1L, new ArrayList<>(), -1L, -1L);
	}
}
