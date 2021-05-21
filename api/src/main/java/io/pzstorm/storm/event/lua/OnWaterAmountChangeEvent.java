package io.pzstorm.storm.event.lua;

import zombie.iso.IsoObject;

/**
 * Triggered when water amount changes for water containers.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnWaterAmountChangeEvent implements LuaEvent {

	/**
	 * Water container being updated.
	 */
	public final IsoObject container;

	/**
	 * Amount of water before update.
	 */
	public final Integer oldWaterAmount;

	public OnWaterAmountChangeEvent(IsoObject container, Integer oldWaterAmount) {

		this.container = container;
		this.oldWaterAmount = oldWaterAmount;
	}
}
