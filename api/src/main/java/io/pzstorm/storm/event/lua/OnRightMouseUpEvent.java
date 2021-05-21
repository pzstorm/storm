package io.pzstorm.storm.event.lua;

/**
 * Triggered when mouse button is released.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnRightMouseUpEvent implements LuaEvent {

	/**
	 * Position of mouse along x-axis.
	 */
	public final Integer x;

	/**
	 * Position of mouse along y-axis.
	 */
	public final Integer y;

	public OnRightMouseUpEvent(Integer x, Integer y) {

		this.x = x;
		this.y = y;
	}
}
