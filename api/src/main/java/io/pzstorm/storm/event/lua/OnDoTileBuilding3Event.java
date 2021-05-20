package io.pzstorm.storm.event.lua;

import se.krka.kahlua.vm.KahluaTable;
import zombie.iso.IsoGridSquare;

@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnDoTileBuilding3Event implements LuaEvent {

	// TODO: document this event
	public final KahluaTable table;
	public final boolean render;
	public final int x, y, z;

	public OnDoTileBuilding3Event(KahluaTable table, boolean render, int x, int y, int z) {
		this.table = table;
		this.render = render;
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
