package io.pzstorm.storm.event.lua;

import zombie.radio.devices.WaveSignalDevice;

@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnDeviceTextEvent implements LuaEvent {

	// TODO: document this event
	public final WaveSignalDevice device;
	public final String text1, text2;
	public final Float x, y, z;

	public OnDeviceTextEvent(String text1, Integer x, Integer y, Integer z, String text2, WaveSignalDevice device) {
		this.text1 = text1;
		this.x = (float)x;
		this.y = (float)y;
		this.z = (float)z;
		this.text2 = text2;
		this.device = device;
	}

	public OnDeviceTextEvent(String text1, Float x, Float y, WaveSignalDevice device) {
		this.text1 = text1;
		this.x = x;
		this.y = y;
		this.z = -1f;
		this.text2 = "";
		this.device = device;
	}
}
