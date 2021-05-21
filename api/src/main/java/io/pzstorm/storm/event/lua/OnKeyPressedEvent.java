package io.pzstorm.storm.event.lua;

/**
 * Called when key is pressed.
 *
 * @see OnKeyStartPressedEvent
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnKeyPressedEvent implements LuaEvent {

	/**
	 * <p>Integer denoting the key being pressed.</p>
	 * See {@code org.lwjgl.input.Keyboard} for list of key codes.
	 */
	public final Integer key;

	public OnKeyPressedEvent(Integer key) {
		this.key = key;
	}
}
