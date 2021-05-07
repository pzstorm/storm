package io.pzstorm.storm.event;

import io.pzstorm.storm.hook.StormHook;

/**
 * This class represents game events recognized by Storm. These events are created by installed
 * {@link StormHook}s and included as a method parameter in a callback to {@link StormEventDispatcher}.
 * They are then dispatched to all methods that subscribe to those specific events.
 */
public interface ZomboidEvent {

	/**
	 * Returns a readable name that identifies this event.
	 */
	String getName();
}
