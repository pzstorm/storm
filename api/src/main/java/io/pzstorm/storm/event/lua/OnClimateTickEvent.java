package io.pzstorm.storm.event.lua;

import zombie.iso.weather.ClimateManager;

/**
 * Triggered every time {@link ClimateManager} is updated.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnClimateTickEvent implements LuaEvent {

	/**
	 * {@link ClimateManager} that is being updated.
	 */
	public final ClimateManager manager;

	public OnClimateTickEvent(ClimateManager manager) {
		this.manager = manager;
	}
}