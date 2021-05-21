package io.pzstorm.storm.event.lua;

// TODO: document this event
@SuppressWarnings({ "WeakerAccess", "unused" })
public class SyncFactionEvent implements LuaEvent {

	public final String var1;

	public SyncFactionEvent(String var1) {
		this.var1 = var1;
	}
}
