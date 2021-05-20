package io.pzstorm.storm.event.lua;

import zombie.iso.weather.ClimateManager;

/**
 * Triggered after {@link ClimateManager} has been initialized.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnClimateManagerInitEvent implements LuaEvent {

	/**
	 * {@link ClimateManager} that has just initialized.
	 */
	public final ClimateManager manager;

	public OnClimateManagerInitEvent(ClimateManager manager) {
		this.manager = manager;
	}
}
