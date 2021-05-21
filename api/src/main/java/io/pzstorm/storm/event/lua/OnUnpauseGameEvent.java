package io.pzstorm.storm.event.lua;

/**
 * Triggers after the game gets unpaused.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnUnpauseGameEvent implements LuaEvent {

	@Override
	public String getName() {
		return "OnServerFinishSaving";
	}
}
