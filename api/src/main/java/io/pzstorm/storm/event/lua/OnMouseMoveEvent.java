package io.pzstorm.storm.event.lua;

/**
 * Triggered when mouse is moved.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnMouseMoveEvent implements LuaEvent {

	// TODO: finish documenting this event
	public final Double xA;
	public final Double yA;
	public final Double x;
	public final Double y;

	public OnMouseMoveEvent(Double xA, Double yA, Double x, Double y) {
		this.xA = xA;
		this.yA = yA;
		this.x = x;
		this.y = y;
	}
}
