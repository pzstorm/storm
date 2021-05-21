package io.pzstorm.storm.event.lua;

import zombie.iso.IsoObject;

/**
 * Triggered after {@link IsoObject} has been added to world.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnObjectAddedEvent implements LuaEvent {

	/**
	 * Object that was just added to world.
	 */
	public final IsoObject object;

	public OnObjectAddedEvent(IsoObject object) {
		this.object = object;
	}
}
