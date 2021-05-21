package io.pzstorm.storm.event.lua;

/**
 * Triggered when a controller is activated.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnJoypadActivateEvent implements LuaEvent {

	/**
	 * The ID of the activated controller.
	 */
	public final Integer joypadID;

	public OnJoypadActivateEvent(Integer joypadID) {
		this.joypadID = joypadID;
	}
}
