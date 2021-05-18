package io.pzstorm.storm.event.lua;

import zombie.characters.IsoGameCharacter;
import zombie.inventory.types.HandWeapon;

/**
 * Triggered when a {@link IsoGameCharacter} has been hit by a {@link HandWeapon}.
 *
 * @see OnWeaponSwingEvent
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnWeaponHitCharacterEvent implements LuaEvent {

	public final IsoGameCharacter attacker;
	public final IsoGameCharacter target;
	public final HandWeapon weapon;
	public final float damage;

	public OnWeaponHitCharacterEvent(IsoGameCharacter attacker, IsoGameCharacter target, HandWeapon weapon, float damage) {

		this.attacker = attacker;
		this.target = target;
		this.weapon = weapon;
		this.damage = damage;
	}
}
