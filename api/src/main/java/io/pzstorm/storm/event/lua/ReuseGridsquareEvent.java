package io.pzstorm.storm.event.lua;

import zombie.iso.IsoGridSquare;

@SuppressWarnings({ "WeakerAccess", "unused" })
public class ReuseGridsquareEvent implements LuaEvent {

	// TODO: document this event
	public final IsoGridSquare gridSquare;

	public ReuseGridsquareEvent(IsoGridSquare gridSquare) {
		this.gridSquare = gridSquare;
	}
}
