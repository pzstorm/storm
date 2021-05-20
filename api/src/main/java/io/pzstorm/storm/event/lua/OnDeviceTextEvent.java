package io.pzstorm.storm.event.lua;

import zombie.radio.devices.WaveSignalDevice;

@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnDeviceTextEvent implements LuaEvent {

	// TODO: document this event
	public final WaveSignalDevice device;
	public final String text1, text2;
	public final int x, y, z;

	public OnDeviceTextEvent(String text1, int x, int y, int z, String text2, WaveSignalDevice device) {
		this.text1 = text1;
		this.x = x;
		this.y = y;
		this.z = z;
		this.text2 = text2;
		this.device = device;
	}
}
