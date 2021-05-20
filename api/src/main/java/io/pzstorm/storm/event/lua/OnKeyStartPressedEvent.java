package io.pzstorm.storm.event.lua;

/**
 * Called when key is initially pressed.
 *
 * @see OnKeyPressedEvent
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnKeyStartPressedEvent implements LuaEvent {

	/**
	 * <p>Integer denoting the key being pressed.</p>
	 * See {@code org.lwjgl.input.Keyboard} for list of key codes.
	 */
	public final int key;

	public OnKeyStartPressedEvent(int key) {
		this.key = key;
	}
}
