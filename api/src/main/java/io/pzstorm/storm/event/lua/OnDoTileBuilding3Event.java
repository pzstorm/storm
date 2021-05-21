package io.pzstorm.storm.event.lua;

import se.krka.kahlua.vm.KahluaTable;

@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnDoTileBuilding3Event implements LuaEvent {

	// TODO: document this event
	public final KahluaTable table;
	public final Boolean render;
	public final Integer x, y, z;

	public OnDoTileBuilding3Event(KahluaTable table, Boolean render, Integer x, Integer y, Integer z) {

		this.table = table;
		this.render = render;
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
