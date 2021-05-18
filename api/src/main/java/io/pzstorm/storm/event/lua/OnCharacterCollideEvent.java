package io.pzstorm.storm.event.lua;

import zombie.characters.IsoGameCharacter;
import zombie.iso.IsoMovingObject;
import zombie.iso.IsoObject;

/**
 * Called when an object collides with {@link IsoGameCharacter}.
 *
 * @see OnObjectCollideEvent
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnCharacterCollideEvent implements LuaEvent {

	public final IsoMovingObject object;
	public final IsoObject character;

	public OnCharacterCollideEvent(IsoMovingObject object, IsoObject character) {

		this.object = object;
		this.character = character;
	}
}
