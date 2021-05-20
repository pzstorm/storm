package io.pzstorm.storm.event.lua;

import zombie.characters.IsoGameCharacter;
import zombie.characters.IsoPlayer;

/**
 * Called when {@link IsoPlayer} successfully enters a vehicle.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnEnterVehicleEvent implements LuaEvent {

	/**
	 * The player that entered the vehicle.
	 */
	public final IsoGameCharacter player;

	public OnEnterVehicleEvent(IsoGameCharacter player) {
		this.player = player;
	}
}
