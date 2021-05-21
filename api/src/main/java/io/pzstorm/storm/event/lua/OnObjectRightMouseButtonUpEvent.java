package io.pzstorm.storm.event.lua;

import zombie.iso.IsoObject;

/**
 * Triggered when right mouse button is released on {@link IsoObject}.
 *
 * @see OnObjectRightMouseButtonDownEvent
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnObjectRightMouseButtonUpEvent implements LuaEvent {

	/**
	 * Object clicked on.
	 */
	public final IsoObject object;

	/**
	 * Position of mouse along x-axis.
	 */
	public final Integer x;

	/**
	 * Position of mouse along y-axis.
	 */
	public final Integer y;

	public OnObjectRightMouseButtonUpEvent(IsoObject object, Integer x, Integer y) {
		this.object = object;
		this.x = x;
		this.y = y;
	}
}
