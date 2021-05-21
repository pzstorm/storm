package io.pzstorm.storm.event.lua;

import zombie.characters.IsoPlayer;
import zombie.inventory.InventoryItem;

// TODO: document this event
@SuppressWarnings({ "WeakerAccess", "unused" })
public class TradingUIAddItemEvent implements LuaEvent {

	public final IsoPlayer var1;
	public final InventoryItem var2;

	public TradingUIAddItemEvent(IsoPlayer var1, InventoryItem var2) {

		this.var1 = var1;
		this.var2 = var2;
	}
}
