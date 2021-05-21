package io.pzstorm.storm.event.lua;

import zombie.ai.State;
import zombie.characters.IsoGameCharacter;

/**
 * Triggered before an AI state changes.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnAIStateChangeEvent implements LuaEvent {

	/**
	 * Character that owns the state machine.
	 */
	public final IsoGameCharacter character;

	/**
	 * The new AI state.
	 */
	public final State newState;

	/**
	 * The previous AI state.
	 */
	public final State prevState;

	public OnAIStateChangeEvent(IsoGameCharacter character, State newState, State prevState) {

		this.character = character;
		this.newState = newState;
		this.prevState = prevState;
	}
}
