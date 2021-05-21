package io.pzstorm.storm.event.lua;

import java.util.ArrayList;

/**
 * Triggers when Steam workshop item is being processed.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnServerWorkshopItemsEvent implements LuaEvent {

	public final String state, errorReason;
	public final Long workshopItemID;
	public final ArrayList<String> itemIDs;
	public final Long var1, var2;

	public OnServerWorkshopItemsEvent(String state, String errorReason, Long workshopItemID,
									  ArrayList<String> itemIDs, Long var1, Long var2) {

		this.state = state;
		this.errorReason = errorReason;
		this.workshopItemID = workshopItemID;
		this.itemIDs = itemIDs;
		this.var1 = var1;
		this.var2 = var2;
	}

	public OnServerWorkshopItemsEvent(String state, String workshopItemID, Long var1, Long var2) {
		this(state, "", Long.parseLong(workshopItemID), new ArrayList<>(), var1, var2);
	}

	public OnServerWorkshopItemsEvent(String state, ArrayList<String> itemIDs) {
		this(state, "", -1L, itemIDs, -1L, -1L);
	}

	public OnServerWorkshopItemsEvent(String state, String errorReason) {
		this(state, errorReason, -1L, new ArrayList<>(), -1L, -1L);
	}

	public OnServerWorkshopItemsEvent(String state, long workshopItemID, String errorReason) {
		this(state, errorReason, workshopItemID, new ArrayList<>(), -1L, -1L);
	}

	public OnServerWorkshopItemsEvent(String state) {
		this(state, "", -1L, new ArrayList<>(), -1L, -1L);
	}
}
