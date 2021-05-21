package io.pzstorm.storm.event.lua;

/**
 * Triggered when a chat tab has been removed.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnTabRemovedEvent implements LuaEvent {

	/**
	 * Title of the tab being added.
	 */
	public final String tabTitle;

	/**
	 * ID of the tab being removed.
	 */
	public final Short tabID;

	public OnTabRemovedEvent(String tabTitle, Short tabID) {

		this.tabTitle = tabTitle;
		this.tabID = tabID;
	}
}
