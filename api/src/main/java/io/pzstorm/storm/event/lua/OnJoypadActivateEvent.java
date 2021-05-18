package io.pzstorm.storm.event.lua;

/**
 * Triggered when a controller is activated.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnJoypadActivateEvent implements LuaEvent {

	/**
	 * The ID of the activated controller.
	 */
	public final int joypadID;

	public OnJoypadActivateEvent(int joypadID) {
		this.joypadID = joypadID;
	}
}
