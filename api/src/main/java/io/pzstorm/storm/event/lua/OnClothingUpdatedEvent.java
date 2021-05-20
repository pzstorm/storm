package io.pzstorm.storm.event.lua;

import zombie.characters.IsoGameCharacter;

/**
 * Triggered when a character's clothing items are updated.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnClothingUpdatedEvent implements LuaEvent {

	/**
	 * The character whose clothing was updated.
	 */
	public final IsoGameCharacter character;

	public OnClothingUpdatedEvent(IsoGameCharacter character) {
		this.character = character;
	}
}
