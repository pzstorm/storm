package io.pzstorm.storm.event.lua;

import java.util.ArrayList;

/**
 * Triggered when game client receives user log.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnReceiveUserlogEvent implements LuaEvent {

	// TODO: finish documenting this event
	public final String identifier;
	public final ArrayList<?> userLogs;

	public OnReceiveUserlogEvent(String identifier, ArrayList<?> userLogs) {

		this.identifier = identifier;
		this.userLogs = userLogs;
	}
}
