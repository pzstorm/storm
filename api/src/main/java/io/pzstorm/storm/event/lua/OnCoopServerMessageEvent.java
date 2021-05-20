package io.pzstorm.storm.event.lua;

@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnCoopServerMessageEvent implements LuaEvent {

	// TODO: document this event
	public final String var1, var2, var3;

	public OnCoopServerMessageEvent(String var1, String var2, String var3) {
		this.var1 = var1;
		this.var2 = var2;
		this.var3 = var3;
	}
}
