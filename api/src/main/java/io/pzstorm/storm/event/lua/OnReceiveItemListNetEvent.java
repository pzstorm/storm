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

import zombie.characters.IsoPlayer;

// TODO: document this event
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnReceiveItemListNetEvent implements LuaEvent {

	public final IsoPlayer player1, player2;
	public final ArrayList<?> list;
	public final String text1, text2;

	public OnReceiveItemListNetEvent(IsoPlayer player1, ArrayList<?> list, IsoPlayer player2, String text1, String text2) {

		this.player1 = player1;
		this.list = list;
		this.player2 = player2;
		this.text1 = text1;
		this.text2 = text2;
	}
}
