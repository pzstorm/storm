package io.pzstorm.storm.event.lua;

import zombie.iso.objects.IsoFire;

/**
 * Triggered when new instance of {@link IsoFire} is created.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnNewFireEvent implements LuaEvent {

	/**
	 * Instance of {@link IsoFire} just created.
	 */
	public final IsoFire isoFire;

	public OnNewFireEvent(IsoFire isoFire) {
		this.isoFire = isoFire;
	}
}
