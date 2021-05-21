package io.pzstorm.storm.event.lua;

import se.krka.kahlua.vm.KahluaTable;

/**
 * Triggered when spawn regions are loaded in Lua.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnSpawnRegionsLoadedEvent implements LuaEvent {

	/**
	 * Table with loaded regions.
	 */
	public final KahluaTable table;

	public OnSpawnRegionsLoadedEvent(KahluaTable table) {
		this.table = table;
	}
}
