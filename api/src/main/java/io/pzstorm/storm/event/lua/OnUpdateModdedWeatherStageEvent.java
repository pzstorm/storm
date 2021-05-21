package io.pzstorm.storm.event.lua;

import zombie.iso.weather.WeatherPeriod;

// TODO: document this event
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnUpdateModdedWeatherStageEvent implements LuaEvent {

	public final WeatherPeriod period;
	public final WeatherPeriod.WeatherStage stage;
	public final Float airFrontStrength;

	public OnUpdateModdedWeatherStageEvent(WeatherPeriod period, WeatherPeriod.WeatherStage stage, Float airFrontStrength) {
		this.period = period;
		this.stage = stage;
		this.airFrontStrength = airFrontStrength;
	}
}
