package io.pzstorm.storm.event.lua;

import se.krka.kahlua.vm.KahluaTable;
import zombie.iso.BuildingDef;

/**
 * Currently unknown use.
 *
 * @see OnMultiTriggerNPCEvent
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnTriggerNPCEvent implements LuaEvent {

	public final String type;
	public final KahluaTable data;
	public final BuildingDef def;

	public OnTriggerNPCEvent(String type, KahluaTable data, BuildingDef def) {

		this.type = type;
		this.data = data;
		this.def = def;
	}

	@Override
	public String getName() {
		return "OnTriggerNPCEvent";
	}
}
