package io.pzstorm.storm.event.lua;

import zombie.characters.IsoPlayer;
import zombie.inventory.types.HandWeapon;

/**
 * Triggered after the player presses the weapon rack button.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnPressRackButtonEvent implements LuaEvent {

	/**
	 * The player that is racking the weapon.
	 */
	public final IsoPlayer player;

	/**
	 * The weapon that is being racked.
	 */
	public final HandWeapon weapon;

	public OnPressRackButtonEvent(IsoPlayer player, HandWeapon weapon) {

		this.player = player;
		this.weapon = weapon;
	}
}
