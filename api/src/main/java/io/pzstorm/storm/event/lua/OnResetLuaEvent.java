package io.pzstorm.storm.event.lua;

/**
 * Trigger after resetting Lua scripts.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnResetLuaEvent implements LuaEvent {

	// TODO: finish documenting this event
	public final String var1;

	public OnResetLuaEvent(String var1) {
		this.var1 = var1;
	}
}
