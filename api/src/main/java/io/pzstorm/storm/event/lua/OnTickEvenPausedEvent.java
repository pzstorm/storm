package io.pzstorm.storm.event.lua;

/**
 * Same as {@link OnTickEvent}, but triggered when the game is paused as well.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnTickEvenPausedEvent implements LuaEvent {

	public final Double ticksElapsed;

	public OnTickEvenPausedEvent(Double ticksElapsed) {
		this.ticksElapsed = ticksElapsed;
	}
}
