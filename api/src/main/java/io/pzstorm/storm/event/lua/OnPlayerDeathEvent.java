package io.pzstorm.storm.event.lua;

import zombie.characters.IsoPlayer;

/**
 * Triggered when a player dies.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnPlayerDeathEvent implements LuaEvent {

	/**
	 * The player which died.
	 */
	public final IsoPlayer player;

	public OnPlayerDeathEvent(IsoPlayer player) {
		this.player = player;
	}
}
