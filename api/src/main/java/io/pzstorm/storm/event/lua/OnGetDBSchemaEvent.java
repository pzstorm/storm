package io.pzstorm.storm.event.lua;

import se.krka.kahlua.vm.KahluaTable;

@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnGetDBSchemaEvent implements LuaEvent {

	// TODO: document this event
	public final KahluaTable dbSchema;

	public OnGetDBSchemaEvent(KahluaTable dbSchema) {
		this.dbSchema = dbSchema;
	}
}
