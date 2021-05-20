package io.pzstorm.storm.event.lua;

import se.krka.kahlua.vm.KahluaTable;

@SuppressWarnings({ "WeakerAccess", "unused" })
public class MngInvReceiveItemsEvent implements LuaEvent {

	// TODO: document this event
	public final KahluaTable table;

	public MngInvReceiveItemsEvent(KahluaTable table) {
		this.table = table;
	}
}
