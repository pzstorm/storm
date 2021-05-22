package io.pzstorm.storm.event.lua;

/**
 * Triggers after the game gets paused.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnServerStartSavingEvent implements LuaEvent {

	@Override
	public String getName() {
		return "OnServerStartSaving";
	}
}
