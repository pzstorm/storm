package io.pzstorm.storm.event.lua;

import zombie.iso.IsoObject;

/**
 * Triggered before an {@link IsoObject} is removed from world.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnObjectAboutToBeRemovedEvent implements LuaEvent {

	/**
	 * Object to be removed from world.
	 */
	public final IsoObject object;

	public OnObjectAboutToBeRemovedEvent(IsoObject object) {
		this.object = object;
	}
}
