package io.pzstorm.storm.event.lua;

/**
 * Triggered when right mouse button is down.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnRightMouseDownEvent implements LuaEvent {

	/**
	 * Position of mouse along x-axis.
	 */
	public final Integer x;

	/**
	 * Position of mouse along y-axis.
	 */
	public final Integer y;

	public OnRightMouseDownEvent(Integer x, Integer y) {

		this.x = x;
		this.y = y;
	}
}
