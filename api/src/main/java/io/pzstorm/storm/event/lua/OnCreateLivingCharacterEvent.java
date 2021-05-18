package io.pzstorm.storm.event.lua;

import zombie.characters.IsoPlayer;
import zombie.characters.IsoSurvivor;
import zombie.characters.SurvivorDesc;

/**
 * Called when {@link IsoPlayer} or {@link IsoSurvivor} is being created.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnCreateLivingCharacterEvent implements LuaEvent {

	public final IsoPlayer player;
	public final SurvivorDesc descriptor;

	public OnCreateLivingCharacterEvent(IsoPlayer player, SurvivorDesc descriptor) {
		this.player = player;
		this.descriptor = descriptor;
	}
}
