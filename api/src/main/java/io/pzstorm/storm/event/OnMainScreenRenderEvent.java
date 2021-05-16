package io.pzstorm.storm.event;

import zombie.gameStates.MainScreenState;

/**
 * This event fires on each tick when the main screen is being rendered.
 *
 * @see MainScreenState#render()
 */
@SuppressWarnings("unused")
public class OnMainScreenRenderEvent implements ZomboidEvent {

	@Override
	public String getName() {
		return "onMainScreenRender";
	}

	@Override
	public String toString() {
		return getName();
	}
}
