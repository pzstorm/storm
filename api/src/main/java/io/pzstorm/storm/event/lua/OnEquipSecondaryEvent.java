package io.pzstorm.storm.event.lua;

import zombie.characters.IsoGameCharacter;
import zombie.inventory.InventoryItem;

/**
 * Triggered when a character equips an item in its secondary slot.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnEquipSecondaryEvent implements LuaEvent {

	/**
	 * The character which equipped the item.
	 */
	public final IsoGameCharacter player;

	/**
	 * The item which was equipped.
	 */
	public final InventoryItem item;

	public OnEquipSecondaryEvent(IsoGameCharacter player, InventoryItem item) {

		this.player = player;
		this.item = item;
	}
}
