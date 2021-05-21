package io.pzstorm.storm.event.lua;

import zombie.characters.IsoGameCharacter;
import zombie.characters.IsoPlayer;
import zombie.characters.IsoSurvivor;
import zombie.characters.SurvivorDesc;

/**
 * Called when {@link IsoPlayer} or {@link IsoSurvivor} is being created.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnCreateLivingCharacterEvent implements LuaEvent {

	public final IsoGameCharacter character;
	public final SurvivorDesc descriptor;

	public OnCreateLivingCharacterEvent(IsoGameCharacter character, SurvivorDesc descriptor) {
		this.character = character;
		this.descriptor = descriptor;
	}
}
