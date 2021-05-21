package io.pzstorm.storm.event.lua;

import zombie.erosion.ErosionMain;
import zombie.erosion.season.ErosionSeason;

/**
 * Triggered after seasons have been initialized.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnInitSeasonsEvent implements LuaEvent {

	/**
	 * Instance of season that was just initialized.
	 */
	public final ErosionSeason season;

	public OnInitSeasonsEvent(ErosionSeason season) {
		this.season = season;
	}
}
