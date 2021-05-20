package io.pzstorm.storm.event.lua;

import zombie.characters.IsoGameCharacter;

/**
 * Triggered when an AI state is exited.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnAIStateExitEvent implements LuaEvent {

	/**
	 * The character the state was executed on.
	 */
	public final IsoGameCharacter character;

	public OnAIStateExitEvent(IsoGameCharacter character) {
		this.character = character;
	}
}
