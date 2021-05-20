package io.pzstorm.storm.event.lua;

import zombie.characters.IsoGameCharacter;

/**
 * Triggered when an AI state is entered.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnAIStateEnterEvent implements LuaEvent {

	/**
	 * The character the state will execute on.
	 */
	public final IsoGameCharacter character;

	public OnAIStateEnterEvent(IsoGameCharacter character) {
		this.character = character;
	}
}
