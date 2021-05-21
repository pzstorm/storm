package io.pzstorm.storm.event.lua;

import zombie.characters.IsoGameCharacter;
import zombie.characters.IsoZombie;

/**
 * Called after a {@link IsoZombie} dies.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnZombieDeadEvent implements LuaEvent {

	public final IsoGameCharacter zombie;

	public OnZombieDeadEvent(IsoGameCharacter zombie) {
		this.zombie = zombie;
	}
}
