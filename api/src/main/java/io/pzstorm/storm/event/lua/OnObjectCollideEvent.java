package io.pzstorm.storm.event.lua;

import zombie.iso.IsoMovingObject;
import zombie.iso.IsoObject;

/**
 * Called when {@link IsoMovingObject} collides with another {@link IsoObject}.
 *
 * @see OnCharacterCollideEvent
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnObjectCollideEvent implements LuaEvent {

	public final IsoMovingObject collider;
	public final IsoObject target;

	public OnObjectCollideEvent(IsoMovingObject collider, IsoObject target) {

		this.collider = collider;
		this.target = target;
	}
}
