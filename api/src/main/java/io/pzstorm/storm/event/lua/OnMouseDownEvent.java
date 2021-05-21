package io.pzstorm.storm.event.lua;

/**
 * Triggered when mouse button is down.
 *
 * @see OnMouseMoveEvent
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnMouseDownEvent implements LuaEvent {

	/**
	 * Position of the mouse along x-axis.
	 */
	public final Integer x;

	/**
	 * Position of the mouse along y-axis.
	 */
	public final Integer y;

	public OnMouseDownEvent(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}
}
