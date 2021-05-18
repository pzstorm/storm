package io.pzstorm.storm.event.lua;

/**
 * Same as {@link OnTickEvent}, but triggered when the game is paused as well.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnTickEvenPausedEvent implements LuaEvent {

	public final double ticksElapsed;

	public OnTickEvenPausedEvent(double ticksElapsed) {
		this.ticksElapsed = ticksElapsed;
	}
}
