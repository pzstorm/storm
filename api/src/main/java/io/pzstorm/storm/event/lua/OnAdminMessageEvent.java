package io.pzstorm.storm.event.lua;

/**
 * Triggers when a player receives an admin message.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnAdminMessageEvent implements LuaEvent {

	public final String message;
	public final int var1, var2, var3;

	public OnAdminMessageEvent(String message, int var1, int var2, int var3) {
		this.message = message;
		this.var1 = var1;
		this.var2 = var2;
		this.var3 = var3;
	}
}
