package io.pzstorm.storm.event.lua;

import zombie.iso.weather.WeatherPeriod;

/**
 * Triggered when creating <b>modded</b> {@link WeatherPeriod} stage.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnInitModdedWeatherStageEvent implements LuaEvent {

	// TODO: finish documenting this event
	public final WeatherPeriod period;
	public final WeatherPeriod.WeatherStage stage;
	public final Float airFrontStrength;

	public OnInitModdedWeatherStageEvent(WeatherPeriod period, WeatherPeriod.WeatherStage stage, Float airFrontStrength) {
		this.period = period;
		this.stage = stage;
		this.airFrontStrength = airFrontStrength;
	}
}
