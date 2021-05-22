/*
 * Zomboid Storm - Java modding toolchain for Project Zomboid
 * Copyright (C) 2021 Matthew Cain
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

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
