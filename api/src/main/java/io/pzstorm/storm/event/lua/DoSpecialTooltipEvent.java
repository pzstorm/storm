package io.pzstorm.storm.event.lua;

import zombie.iso.IsoGridSquare;
import zombie.iso.IsoObject;
import zombie.ui.ObjectTooltip;

/**
 * This event triggers when {@link IsoObject} renders special tooltip.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class DoSpecialTooltipEvent implements LuaEvent {

	/**
	 * The tooltip being rendered.
	 */
	public final ObjectTooltip tooltip;

	/**
	 * Location of {@link IsoObject} being rendered.
	 */
	public final IsoGridSquare square;

	public DoSpecialTooltipEvent(ObjectTooltip tooltip, IsoGridSquare square) {

		this.tooltip = tooltip;
		this.square = square;
	}
}
