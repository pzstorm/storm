package io.pzstorm.storm.event.lua;

import zombie.characters.IsoPlayer;

/**
 * Triggered while the player is moving.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnPlayerMoveEvent implements LuaEvent {

	/**
	 * The player that is moving.
	 */
	public final IsoPlayer player;

	public OnPlayerMoveEvent(IsoPlayer player) {
		this.player = player;
	}
}
