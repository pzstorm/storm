package io.pzstorm.storm.event.lua;

import se.krka.kahlua.vm.KahluaTable;

/**
 * Triggered when client receives server command.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnServerCommandEvent implements LuaEvent {

	// TODO: finish documenting this event
	public final String var1, var2;
	public final KahluaTable var3;

	public OnServerCommandEvent(String var1, String var2, KahluaTable var3) {
		this.var1 = var1;
		this.var2 = var2;
		this.var3 = var3;
	}
}
