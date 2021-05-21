package io.pzstorm.storm.event.lua;

@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnSetDefaultTabEvent implements LuaEvent {

	// TODO: document this event
	public final String tabName;

	public OnSetDefaultTabEvent(String tabName) {
		this.tabName = tabName;
	}
}
