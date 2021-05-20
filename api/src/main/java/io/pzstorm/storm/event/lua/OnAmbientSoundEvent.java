package io.pzstorm.storm.event.lua;

/**
 * Triggers when an ambient sound is constructed.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnAmbientSoundEvent implements LuaEvent {

	/**
	 * Name of the ambient sound being constructed.
	 */
	public final String soundName;

	/**
	 * Position of ambient sound along x-axis.
	 */
	public final float x;

	/**
	 * Position of ambient sound along y-axis.
	 */
	public final float y;

	public OnAmbientSoundEvent(String soundName, float x, float y) {
		this.soundName = soundName;
		this.x = x;
		this.y = y;
	}
}
