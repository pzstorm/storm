package io.pzstorm.storm.event.lua;

import zombie.characters.BodyDamage.BodyPartType;
import zombie.characters.IsoGameCharacter;
import zombie.characters.IsoZombie;
import zombie.inventory.types.HandWeapon;

/**
 * Triggered when {@link IsoZombie} was hit by {@link IsoGameCharacter}.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnHitZombieEvent implements LuaEvent {

	/**
	 * Instance of zombie being attacked.
	 */
	public final IsoZombie zombie;

	/**
	 * Instance of character attacking zombie.
	 */
	public final IsoGameCharacter attacker;

	/**
	 * Body part that has been hit.
	 */
	public final BodyPartType target;

	/**
	 * Weapon used to hit zombie.
	 */
	public final HandWeapon weapon;

	public OnHitZombieEvent(IsoZombie zombie, IsoGameCharacter attacker, BodyPartType target, HandWeapon weapon) {

		this.zombie = zombie;
		this.attacker = attacker;
		this.target = target;
		this.weapon = weapon;
	}
}
