package io.pzstorm.storm.event.lua;

/**
 * <p>Triggers when a unmapped keyboard key is releases.</p>
 * This event is exactly the same as {@link OnCustomUIKeyReleasedEvent}.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnCustomUIKeyEvent implements LuaEvent {

	/**
	 * <p>Integer denoting the key being released.</p>
	 * See {@code org.lwjgl.input.Keyboard} for list of key codes.
	 */
	public final int key;

	public OnCustomUIKeyEvent(int key) {
		this.key = key;
	}
}
