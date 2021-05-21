package io.pzstorm.storm.event.lua;

/**
 * Triggered when a chat tab has been added.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnTabAddedEvent implements LuaEvent {

	/**
	 * Title of the tab being added.
	 */
	public final String tabTitle;

	/**
	 * ID of the tab being added.
	 */
	public final Short tabID;

	public OnTabAddedEvent(String tabTitle, Short tabID) {
		this.tabTitle = tabTitle;
		this.tabID = tabID;
	}
}
