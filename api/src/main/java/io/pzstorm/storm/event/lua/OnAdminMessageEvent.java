package io.pzstorm.storm.event.lua;

/**
 * Triggers when a player receives an admin message.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnAdminMessageEvent implements LuaEvent {

	public final String message;
	public final Integer var1, var2, var3;

	public OnAdminMessageEvent(String message, Integer var1, Integer var2, Integer var3) {

		this.message = message;
		this.var1 = var1;
		this.var2 = var2;
		this.var3 = var3;
	}
}
