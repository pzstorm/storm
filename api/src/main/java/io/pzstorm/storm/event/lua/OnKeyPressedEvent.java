package io.pzstorm.storm.event.lua;

/**
 * Called when key is pressed.
 *
 * @see OnKeyStartPressedEvent
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnKeyPressedEvent implements LuaEvent {

	public final int key;

	public OnKeyPressedEvent(int key) {
		this.key = key;
	}
}
