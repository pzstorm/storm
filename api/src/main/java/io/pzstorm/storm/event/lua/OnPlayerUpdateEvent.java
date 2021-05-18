package io.pzstorm.storm.event.lua;

import zombie.characters.IsoPlayer;

/**
 * Called when {@link IsoPlayer} updates.
 *
 * @see OnZombieUpdateEvent
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnPlayerUpdateEvent implements LuaEvent {

	public final IsoPlayer player;

	public OnPlayerUpdateEvent(IsoPlayer player) {
		this.player = player;
	}
}
