package io.pzstorm.storm.event.lua;

import zombie.characters.IsoGameCharacter;

/**
 * Triggered when {@link IsoGameCharacter} dies.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnCharacterDeathEvent implements LuaEvent {

	/**
	 * Character that just died.
	 */
	public final IsoGameCharacter character;

	public OnCharacterDeathEvent(IsoGameCharacter character) {
		this.character = character;
	}
}
