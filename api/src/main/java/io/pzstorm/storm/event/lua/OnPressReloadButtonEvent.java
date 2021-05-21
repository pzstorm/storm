package io.pzstorm.storm.event.lua;

import zombie.characters.IsoPlayer;
import zombie.inventory.types.HandWeapon;

/**
 * Triggers after the player presses the weapon reload button.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnPressReloadButtonEvent implements LuaEvent {

	/**
	 * The player that is reloading the weapon.
	 */
	public final IsoPlayer player;

	/**
	 * The weapon is being reloaded.
	 */
	public final HandWeapon weapon;

	public OnPressReloadButtonEvent(IsoPlayer player, HandWeapon weapon) {
		this.player = player;
		this.weapon = weapon;
	}
}
