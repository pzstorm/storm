package io.pzstorm.storm.event.lua;

@SuppressWarnings({ "WeakerAccess", "unused" })
public class AcceptedFactionInviteEvent implements LuaEvent {

	// TODO: document this event
	public final String var1, var2;

	public AcceptedFactionInviteEvent(String var1, String var2) {

		this.var1 = var1;
		this.var2 = var2;
	}
}
