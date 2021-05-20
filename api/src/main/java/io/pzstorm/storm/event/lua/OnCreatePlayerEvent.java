package io.pzstorm.storm.event.lua;

import zombie.characters.IsoPlayer;

/**
 * Triggered when a player is created.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnCreatePlayerEvent implements LuaEvent {

	/**
	 * Index of player being created.
	 */
	public final int playerIndex;

	/**
	 * The player which was created.
	 */
	public final IsoPlayer player;

	public OnCreatePlayerEvent(int playerIndex, IsoPlayer player) {
		this.playerIndex = playerIndex;
		this.player = player;
	}
}
