package io.pzstorm.storm.event.lua;

import java.util.ArrayList;

import zombie.characters.IsoPlayer;

// TODO: document this event
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnReceiveItemListNetEvent implements LuaEvent {

	public final IsoPlayer player1, player2;
	public final ArrayList<?> list;
	public final String text1, text2;

	public OnReceiveItemListNetEvent(IsoPlayer player1, ArrayList<?> list, IsoPlayer player2, String text1, String text2) {

		this.player1 = player1;
		this.list = list;
		this.player2 = player2;
		this.text1 = text1;
		this.text2 = text2;
	}
}
