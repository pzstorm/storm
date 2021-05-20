package io.pzstorm.storm.event.lua;

import zombie.inventory.ItemContainer;

/**
 * Triggered after {@link ItemContainer} has being filled.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnFillContainerEvent implements LuaEvent {

	/**
	 * Name of the room in which the container is located in.
	 */
	public final String roomName;

	/**
	 * Type of container being filled.
	 */
	public final String containerType;

	/**
	 * Instance of container being filled.
	 */
	public final ItemContainer container;

	public OnFillContainerEvent(String roomName, String containerType, ItemContainer container) {
		this.roomName = roomName;
		this.containerType = containerType;
		this.container = container;
	}
}
