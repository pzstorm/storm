package io.pzstorm.storm.event.lua;

import zombie.characters.IsoGameCharacter;
import zombie.characters.skills.PerkFactory;

/**
 * Triggered when a perk is leveled up or down.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class LevelPerkEvent implements LuaEvent {

	/**
	 * The character whose perk is being leveled up or down.
	 */
	public final IsoGameCharacter player;

	/**
	 * The perk being leveled up.
	 */
	public final PerkFactory.Perks perk;

	/**
	 * Perk level after leveling up or down.
	 */
	public final Integer level;

	/**
	 * {@code true} if leveling perk up or {@code false} if leveling down.
	 */
	public final Boolean levelingUp;

	public LevelPerkEvent(IsoGameCharacter player, PerkFactory.Perks perk, Integer level, Boolean levelingUp) {

		this.player = player;
		this.perk = perk;
		this.level = level;
		this.levelingUp = levelingUp;
	}
}
