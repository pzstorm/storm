package io.pzstorm.storm.event.lua;

import zombie.characters.IsoGameCharacter;
import zombie.inventory.types.HandWeapon;

/**
 * Triggered when player attack state finishes.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnPlayerAttackFinishedEvent implements LuaEvent {

	/**
	 * Player who initiates the attack.
	 */
	public final IsoGameCharacter player;

	/**
	 * Weapon used to attack.
	 */
	public final HandWeapon weapon;

	public OnPlayerAttackFinishedEvent(IsoGameCharacter player, HandWeapon weapon) {

		this.player = player;
		this.weapon = weapon;
	}
}
