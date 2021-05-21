package io.pzstorm.storm.event.lua;

import zombie.characters.IsoGameCharacter;
import zombie.inventory.types.HandWeapon;
import zombie.iso.objects.IsoTree;

/**
 * Triggered when an {@link IsoGameCharacter} uses a {@link HandWeapon} to hit an {@link IsoTree}.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnWeaponHitTreeEvent implements LuaEvent {

	/**
	 * Character hitting the tree.
	 */
	public final IsoGameCharacter owner;

	/**
	 * Weapon used to hit the tree.
	 */
	public final HandWeapon weapon;

	public OnWeaponHitTreeEvent(IsoGameCharacter owner, HandWeapon weapon) {
		this.owner = owner;
		this.weapon = weapon;
	}
}
