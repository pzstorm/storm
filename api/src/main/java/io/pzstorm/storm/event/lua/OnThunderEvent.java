package io.pzstorm.storm.event.lua;

/**
 * Triggered before a thunder strike.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnThunderEvent implements LuaEvent {

	// TODO: finish documenting this event
	public final Integer var1, var2;
	public final Boolean var3, var4, var5;

	public OnThunderEvent(Integer var1, Integer var2, Boolean var3, Boolean var4, Boolean var5) {

		this.var1 = var1;
		this.var2 = var2;
		this.var3 = var3;
		this.var4 = var4;
		this.var5 = var5;
	}

	@Override
	public String getName() {
		return "OnThunderEvent";
	}
}
