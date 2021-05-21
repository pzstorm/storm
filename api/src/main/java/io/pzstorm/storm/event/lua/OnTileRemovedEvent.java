package io.pzstorm.storm.event.lua;

import zombie.iso.IsoObject;

/**
 * Triggered after tile object was removed from world.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnTileRemovedEvent implements LuaEvent {

	/**
	 * Object that was removed from world.
	 */
	public final IsoObject object;

	public OnTileRemovedEvent(IsoObject object) {
		this.object = object;
	}
}
