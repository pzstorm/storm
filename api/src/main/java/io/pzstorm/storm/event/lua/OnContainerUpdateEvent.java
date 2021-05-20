package io.pzstorm.storm.event.lua;

import org.jetbrains.annotations.Nullable;
import zombie.iso.IsoGridSquare;

/**
 * Triggered when an inventory container is being updated.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnContainerUpdateEvent implements LuaEvent {

	/**
	 * Position of the inventory container being updated.
	 */
	public final @Nullable IsoGridSquare gridSquare;

	public OnContainerUpdateEvent(@Nullable IsoGridSquare gridSquare) {
		this.gridSquare = gridSquare;
	}

	public OnContainerUpdateEvent() {
		this(null);
	}
}
