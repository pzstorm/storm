package io.pzstorm.storm.event.lua;

import org.jetbrains.annotations.Nullable;

import zombie.iso.objects.IsoThumpable;

/**
 * Triggered when {@link IsoThumpable} object gets destroyed.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnDestroyIsoThumpableEvent implements LuaEvent {

	/**
	 * Object being destroyed.
	 */
	public final IsoThumpable thumpableObject;

	/**
	 * This object is always {@code null}.
	 */
	public final @Nullable Object object;

	public OnDestroyIsoThumpableEvent(IsoThumpable thumpableObject, @Nullable Object object) {

		this.thumpableObject = thumpableObject;
		this.object = object;
	}
}
