package io.pzstorm.storm.event.lua;

/**
 * Triggers when a player accepts invite for co-op play on Steam.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnAcceptInviteEvent implements LuaEvent {

	public final String var1;

	public OnAcceptInviteEvent(String var1) {
		this.var1 = var1;
	}
}
