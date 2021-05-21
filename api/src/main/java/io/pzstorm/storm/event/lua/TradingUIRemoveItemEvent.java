package io.pzstorm.storm.event.lua;

import zombie.characters.IsoPlayer;

// TODO: document this event
@SuppressWarnings({ "WeakerAccess", "unused" })
public class TradingUIRemoveItemEvent implements LuaEvent {

	public final IsoPlayer var1;
	public final Integer var2;

	public TradingUIRemoveItemEvent(IsoPlayer var1, Integer var2) {

		this.var1 = var1;
		this.var2 = var2;
	}
}
