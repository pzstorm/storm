package io.pzstorm.storm.event.lua;

import org.jetbrains.annotations.Nullable;

import zombie.inventory.InventoryItem;
import zombie.iso.IsoGridSquare;
import zombie.iso.IsoObject;

/**
 * Triggered when an inventory container is being updated.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnContainerUpdateEvent implements LuaEvent {

	/**
	 * Position of the inventory container being updated.
	 */
	public final @Nullable IsoGridSquare gridSquare;

	/**
	 * Item that triggered the container update.
	 */
	public final @Nullable InventoryItem item;

	/**
	 * Object that triggered the container update.
	 */
	public final @Nullable IsoObject object;

	private OnContainerUpdateEvent(@Nullable IsoGridSquare gridSquare,
								   @Nullable InventoryItem item, @Nullable IsoObject object) {

		this.gridSquare = gridSquare;
		this.item = item;
		this.object = object;
	}

	public OnContainerUpdateEvent(@Nullable IsoObject deadBody) {
		this(null, null, deadBody);
	}

	public OnContainerUpdateEvent(@Nullable InventoryItem item) {
		this(null, item, null);
	}

	public OnContainerUpdateEvent(@Nullable IsoGridSquare gridSquare) {
		this(gridSquare, null, null);
	}

	public OnContainerUpdateEvent() {
		this(null, null, null);
	}
}
