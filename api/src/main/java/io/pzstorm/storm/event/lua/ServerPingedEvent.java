package io.pzstorm.storm.event.lua;

/**
 * Triggered when server reads info from ping.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class ServerPingedEvent implements LuaEvent {

	// TODO: finish documenting this event
	public final String var1, var2;

	public ServerPingedEvent(String var1, String var2) {

		this.var1 = var1;
		this.var2 = var2;
	}
}
