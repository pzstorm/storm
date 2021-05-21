package io.pzstorm.storm.event.lua;

import zombie.iso.IsoWorld;
import zombie.radio.ZomboidRadio;
import zombie.radio.scripting.RadioScriptManager;

/**
 * Triggered after radio scripts have been loaded in {@link ZomboidRadio}.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnLoadRadioScriptsEvent implements LuaEvent {

	/**
	 * Script manager that loaded the scripts.
	 */
	public final RadioScriptManager scriptManager;

	/**
	 * {@code true} if {@link IsoWorld#SavedWorldVersion} is {@code -1}, {@code false} otherwise.
	 */
	public final Boolean noSaveWorldVersion;

	public OnLoadRadioScriptsEvent(RadioScriptManager scriptManager, Boolean noSaveWorldVersion) {

		this.scriptManager = scriptManager;
		this.noSaveWorldVersion = noSaveWorldVersion;
	}
}
