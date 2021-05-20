package io.pzstorm.storm.event.lua;

/**
 * <p>Triggers when a unmapped keyboard key is releases.</p>
 * This event is exactly the same as {@link OnCustomUIKeyEvent}.
 *
 * @see OnCustomUIKeyPressedEvent
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnCustomUIKeyReleasedEvent implements LuaEvent {

	/**
	 * <p>Integer denoting the key being released.</p>
	 * See {@code org.lwjgl.input.Keyboard} for list of key codes.
	 */
	public final int key;

	public OnCustomUIKeyReleasedEvent(int key) {
		this.key = key;
	}
}
