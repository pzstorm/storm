package io.pzstorm.storm.event.lua;

/**
 * Called every tick, try to not use this one, use {@link EveryTenMinutesEvent}
 * instead because it can create a lot of frame loss/garbage collection.
 *
 * @see OnTickEvenPausedEvent
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnTickEvent implements LuaEvent {

	public final Double ticksElapsed;

	public OnTickEvent(Double ticksElapsed) {
		this.ticksElapsed = ticksElapsed;
	}
}
