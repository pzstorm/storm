package io.pzstorm.storm.event.lua;

import zombie.characters.IsoZombie;

/**
 * Called when {@link IsoZombie} updates.
 *
 * @see OnPlayerUpdateEvent
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnZombieUpdateEvent implements LuaEvent {

	public final IsoZombie zombie;

	public OnZombieUpdateEvent(IsoZombie zombie) {
		this.zombie = zombie;
	}
}
