package io.pzstorm.storm.event.lua;

/**
 * Triggered when access to co-op session was denied to player.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnCoopJoinFailedEvent implements LuaEvent {

	/**
	 * Index of player who was denied access.
	 */
	public final int playerIndex;

	public OnCoopJoinFailedEvent(int playerIndex) {
		this.playerIndex = playerIndex;
	}
}
