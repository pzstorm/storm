package io.pzstorm.storm.event.lua;

import se.krka.kahlua.vm.KahluaTable;
import zombie.characters.IsoPlayer;

/**
 * Triggered when server receives a command from client.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnClientCommandEvent implements LuaEvent {

	public final String var1;
	public final String var2;
	public final IsoPlayer player;
	public final KahluaTable table;

	public OnClientCommandEvent(String var1, String var2, IsoPlayer player, KahluaTable table) {
		this.var1 = var1;
		this.var2 = var2;
		this.player = player;
		this.table = table;
	}
}
