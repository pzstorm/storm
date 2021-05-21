package io.pzstorm.storm.event.lua;

/**
 * Triggered after a floor layer is rendered.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnPostFloorLayerDrawEvent implements LuaEvent {

	/**
	 * The Z-index of the layer which was rendered.
	 */
	public final Integer zIndex;

	public OnPostFloorLayerDrawEvent(Integer zIndex) {
		this.zIndex = zIndex;
	}
}
