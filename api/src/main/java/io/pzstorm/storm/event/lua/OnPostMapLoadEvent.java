package io.pzstorm.storm.event.lua;

import zombie.iso.IsoCell;

/**
 * Triggered after a cell is loaded.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnPostMapLoadEvent implements LuaEvent {

	/**
	 * The cell which was loaded.
	 */
	public final IsoCell isoCell;

	/**
	 * The position of cell along x-axis.
	 */
	public final Integer x;

	/**
	 * The position of cell along y-axis.
	 */
	public final Integer y;

	public OnPostMapLoadEvent(IsoCell isoCell, Integer x, Integer y) {
		this.isoCell = isoCell;
		this.x = x;
		this.y = y;
	}
}
