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

import zombie.chat.ChatMessage;

/**
 * Triggers when a message is added to chat.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnAddMessageEvent implements LuaEvent {

	/**
	 * Message being added to chat.
	 */
	public final ChatMessage message;

	/**
	 * Chat tab ID the message is being added to.
	 */
	public final Short tabID;

	public OnAddMessageEvent(ChatMessage message, Short tabID) {

		this.message = message;
		this.tabID = tabID;
	}
}
