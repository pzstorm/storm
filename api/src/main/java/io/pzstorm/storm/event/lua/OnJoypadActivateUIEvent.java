package io.pzstorm.storm.event.lua;

/**
 * Triggered when a controller is activated in main menu.
 *
 * @see OnJoypadActivateEvent
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnJoypadActivateUIEvent implements LuaEvent {

	/**
	 * The ID of the activated controller.
	 */
	public final Integer joypadID;

	public OnJoypadActivateUIEvent(Integer joypadID) {
		this.joypadID = joypadID;
	}
}
