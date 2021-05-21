package io.pzstorm.storm.event.lua;

import se.krka.kahlua.vm.KahluaTable;

/**
 * Triggered when inventory container windows are being refreshed.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnRefreshInventoryWindowContainersEvent implements LuaEvent {

	/**
	 * Inventory window being refreshed.
	 */
	public final KahluaTable inventoryPage;

	/**
	 * Refresh action being preformed.
	 */
	public final String action;

	public OnRefreshInventoryWindowContainersEvent(KahluaTable inventoryPage, String action) {
		this.inventoryPage = inventoryPage;
		this.action = action;
	}
}
