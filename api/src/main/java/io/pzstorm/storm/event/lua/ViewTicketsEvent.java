package io.pzstorm.storm.event.lua;

import zombie.network.DBTicket;

import java.util.ArrayList;

@SuppressWarnings({ "WeakerAccess", "unused" })
public class ViewTicketsEvent implements LuaEvent {

	public final ArrayList<DBTicket> var1;

	public ViewTicketsEvent(ArrayList<DBTicket> var1) {
		this.var1 = var1;
	}
}
