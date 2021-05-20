package io.pzstorm.storm.event.lua;

import zombie.characters.IsoPlayer;

@SuppressWarnings({ "WeakerAccess", "unused" })
public class AcceptedTradeEvent implements LuaEvent {

	// TODO: document this event
	public final IsoPlayer player;

	public AcceptedTradeEvent(IsoPlayer player) {
		this.player = player;
	}
}
