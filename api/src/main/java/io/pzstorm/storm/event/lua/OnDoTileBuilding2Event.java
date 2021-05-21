package io.pzstorm.storm.event.lua;

import se.krka.kahlua.vm.KahluaTable;
import zombie.iso.IsoGridSquare;

@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnDoTileBuilding2Event implements LuaEvent {

	// TODO: document this event
	public final IsoGridSquare gridSquare;
	public final KahluaTable table;
	public final Boolean render;
	public final Integer x, y, z;

	public OnDoTileBuilding2Event(KahluaTable table, Boolean render, Integer x,
								  Integer y, Integer z, IsoGridSquare gridSquare) {

		this.gridSquare = gridSquare;
		this.table = table;
		this.render = render;
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
