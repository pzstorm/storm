package io.pzstorm.storm.event.lua;

import zombie.characters.IsoGameCharacter;
import zombie.characters.skills.PerkFactory;

/**
 * Triggered when {@link IsoGameCharacter} earns skill experience.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class AddXPEvent implements LuaEvent {

	/**
	 * Game character earning experience.
	 */
	public final IsoGameCharacter player;

	/**
	 * Perk to earn experience for.
	 */
	public final PerkFactory.Perks perk;

	/**
	 * Amount of experience to earn.
	 */
	public final Float xpAmount;

	public AddXPEvent(IsoGameCharacter player, PerkFactory.Perks perk, Float xpAmount) {

		this.player = player;
		this.perk = perk;
		this.xpAmount = xpAmount;
	}
}
