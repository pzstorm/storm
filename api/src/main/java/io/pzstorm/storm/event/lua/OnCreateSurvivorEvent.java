package io.pzstorm.storm.event.lua;

import zombie.characters.IsoSurvivor;

/**
 * Triggered when a survivor is created.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnCreateSurvivorEvent implements LuaEvent {

	/**
	 * The survivor which was created.
	 */
	public final IsoSurvivor survivor;

	public OnCreateSurvivorEvent(IsoSurvivor survivor) {
		this.survivor = survivor;
	}
}
