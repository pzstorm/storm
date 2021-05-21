package io.pzstorm.storm.event.lua;

import java.util.ArrayList;

/**
 * Called when multiplayer scoreboard is updated.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnScoreboardUpdateEvent implements LuaEvent {

	// TODO: finish documenting event
	public final ArrayList<?> var1, var2, var3;

	public OnScoreboardUpdateEvent(ArrayList<?> var1, ArrayList<?> var2, ArrayList<?> var3) {

		this.var1 = var1;
		this.var2 = var2;
		this.var3 = var3;
	}
}
