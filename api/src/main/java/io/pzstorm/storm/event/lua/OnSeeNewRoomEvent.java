package io.pzstorm.storm.event.lua;

import zombie.iso.areas.IsoRoom;

/**
 * Triggered when new room was spotted by player.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnSeeNewRoomEvent implements LuaEvent {

	/**
	 * The new room spotted.
	 */
	public final IsoRoom room;

	public OnSeeNewRoomEvent(IsoRoom room) {
		this.room = room;
	}
}
