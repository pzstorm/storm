package io.pzstorm.storm.event.lua;

/**
 * Triggers when changing game resolution.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnResolutionChangeEvent implements LuaEvent {

	/**
	 * Screen width before the resolution change.
	 */
	public final Integer oldWidth;

	/**
	 * Screen height before the resolution change.
	 */
	public final Integer oldHeight;

	/**
	 * Screen width after the resolution change.
	 */
	public final Integer newWidth;

	/**
	 * Screen height after the resolution change.
	 */
	public final Integer newHeight;

	public OnResolutionChangeEvent(Integer oldWidth, Integer oldHeight, Integer newWidth, Integer newHeight) {

		this.oldWidth = oldWidth;
		this.oldHeight = oldHeight;
		this.newWidth = newWidth;
		this.newHeight = newHeight;
	}
}
