package io.pzstorm.storm.event.lua;

/**
 * Called when key is held down.
 *
 * @see OnKeyPressedEvent
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnKeyKeepPressedEvent implements LuaEvent {

	/**
	 * <p>Integer denoting the key being held down.</p>
	 * See {@code org.lwjgl.input.Keyboard} for list of key codes.
	 */
	public final Integer key;

	public OnKeyKeepPressedEvent(Integer key) {
		this.key = key;
	}
}
