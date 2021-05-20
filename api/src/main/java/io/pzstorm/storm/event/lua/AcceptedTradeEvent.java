package io.pzstorm.storm.event.lua;

import zombie.characters.IsoPlayer;

/**
 * This event is currently not used.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class AcceptedTradeEvent implements LuaEvent {

	public final IsoPlayer player;

	public AcceptedTradeEvent(IsoPlayer player) {
		this.player = player;
	}
}
