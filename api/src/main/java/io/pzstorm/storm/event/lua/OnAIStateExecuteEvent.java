package io.pzstorm.storm.event.lua;

import zombie.characters.IsoGameCharacter;

/**
 * Triggered when an AI state executes.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnAIStateExecuteEvent implements LuaEvent {

	/**
	 * The character the state executed on.
	 */
	public final IsoGameCharacter character;

	public OnAIStateExecuteEvent(IsoGameCharacter character) {
		this.character = character;
	}
}
