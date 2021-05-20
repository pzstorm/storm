package io.pzstorm.storm.event.lua;

/**
 * Called at the different stages that a players connection
 * state changes to the server when initially connecting.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnConnectionStateChangedEvent implements LuaEvent {

	public final String state, message;

	public OnConnectionStateChangedEvent(String state, String message) {
		this.state = state;
		this.message = message;
	}

	public OnConnectionStateChangedEvent(String state) {
		this(state, "");
	}
}
