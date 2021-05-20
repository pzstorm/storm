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
	public final short tabID;

	public OnAddMessageEvent(ChatMessage message, short tabID) {
		this.message = message;
		this.tabID = tabID;
	}
}
