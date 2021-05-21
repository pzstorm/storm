package io.pzstorm.storm.event.lua;

import zombie.characters.IsoPlayer;
import zombie.iso.IsoGridSquare;

/**
 * Triggered after a world is initialized.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnNewGameEvent implements LuaEvent {

	/**
	 * The player that initialized the world.
	 */
	public final IsoPlayer player;

	/**
	 * The {@link IsoGridSquare} the player is located.
	 */
	public final IsoGridSquare gridSquare;

	public OnNewGameEvent(IsoPlayer player, IsoGridSquare gridSquare) {
		this.player = player;
		this.gridSquare = gridSquare;
	}
}
