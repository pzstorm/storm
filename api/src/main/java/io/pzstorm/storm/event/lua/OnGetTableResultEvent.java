package io.pzstorm.storm.event.lua;

import java.lang.reflect.Array;
import java.util.ArrayList;

@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnGetTableResultEvent implements LuaEvent {

	// TODO: document this event
	public final ArrayList<?> var1;
	public final int var2;
	public final String var3;

	public OnGetTableResultEvent(ArrayList<?> var1, int var2, String var3) {
		this.var1 = var1;
		this.var2 = var2;
		this.var3 = var3;
	}
}
