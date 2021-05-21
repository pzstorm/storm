package io.pzstorm.storm.event.lua;

/**
 * Same as {@link OnTickEvent}, except is only called while on the main menu.
 */
@SuppressWarnings("unused")
public class OnFETickEvent implements LuaEvent {

	/**
	 * @param ticks always {@code 0}.
	 */
	public OnFETickEvent(Integer ticks) {
	}
}
