package io.pzstorm.storm.event.lua;

/**
 * Called when key is initially pressed.
 *
 * @see OnKeyPressedEvent
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnKeyStartPressedEvent implements LuaEvent {

	public final int key;

	public OnKeyStartPressedEvent(int key) {
		this.key = key;
	}
}
