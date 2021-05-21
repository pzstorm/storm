package io.pzstorm.storm.event.lua;

import zombie.iso.IsoGridSquare;

/**
 * Triggered on both client and server when mod data is loaded on server.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class onLoadModDataFromServerEvent implements LuaEvent {

	/**
	 * {@link IsoGridSquare} where mod data is being loaded.
	 */
	public final IsoGridSquare gridSquare;

	public onLoadModDataFromServerEvent(IsoGridSquare gridSquare) {
		this.gridSquare = gridSquare;
	}
}
