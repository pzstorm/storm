package io.pzstorm.storm.event.lua;

import zombie.characters.IsoGameCharacter;
import zombie.inventory.types.HandWeapon;

/**
 * Triggered when a {@link HandWeapon} has hit the apex of its swing.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnWeaponSwingHitPointEvent implements LuaEvent {

	/**
	 * Character swinging the weapon.
	 */
	public final IsoGameCharacter owner;

	/**
	 * Weapon being swung.
	 */
	public final HandWeapon weapon;

	public OnWeaponSwingHitPointEvent(IsoGameCharacter owner, HandWeapon weapon) {
		this.owner = owner;
		this.weapon = weapon;
	}
}
