package io.pzstorm.storm.event.lua;

import zombie.iso.IsoGridSquare;

/**
 * Triggered when {@link IsoGridSquare} is loaded.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class LoadGridsquareEvent implements LuaEvent {

	/**
	 * {@link IsoGridSquare} being loaded.
	 */
	public final IsoGridSquare gridSquare;

	public LoadGridsquareEvent(IsoGridSquare gridSquare) {
		this.gridSquare = gridSquare;
	}
}
