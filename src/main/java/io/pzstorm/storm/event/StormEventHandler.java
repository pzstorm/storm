package io.pzstorm.storm.event;

import se.krka.kahlua.j2se.KahluaTableImpl;
import se.krka.kahlua.vm.KahluaTable;
import zombie.core.Core;
import zombie.ui.TextManager;
import zombie.ui.UIFont;

/**
 * This class responds to all events needed for Storm to implement custom features.
 * Note that not all functionality implementations that are weaved into game bytecode is handled here.
 * Sometimes subscribing to events is not enough to alter game behavior and more invasive
 * actions need to be preformed, like editing or removing lines from game code.
 */
@SuppressWarnings("unused")
public class StormEventHandler {

	@SubscribeEvent
	public static void handleUIElementRenderEvent(OnUIElementPreRenderEvent event) {

		if (event.element.Parent != null)
		{
			KahluaTable table = event.element.Parent.table;
			if (table instanceof KahluaTableImpl)
			{
				Object internal = ((KahluaTableImpl)table).delegate.get("internal");
				if (internal instanceof String && internal.equals("VERSIONDETAIL"))
				{
					String text = "Storm version 0.1.0-alpha";
					TextManager.instance.DrawString(UIFont.Small, Core.width - 235.0,
							Core.height - 50.0, text, 1.0, 1.0, 1.0, 0.7);
				}
			}
		}
	}
}
