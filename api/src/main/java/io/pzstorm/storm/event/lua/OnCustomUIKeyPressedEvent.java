package io.pzstorm.storm.event.lua;

/**
 * Triggers when an unmapped keyboard key is pressed.
 *
 * @see OnCustomUIKeyReleasedEvent
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnCustomUIKeyPressedEvent implements LuaEvent {

	/**
	 * <p>Integer denoting the key being pressed.</p>
	 * See {@code org.lwjgl.input.Keyboard} for list of key codes.
	 */
	public final Integer key;

	public OnCustomUIKeyPressedEvent(Integer key) {
		this.key = key;
	}
}
