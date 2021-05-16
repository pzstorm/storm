package io.pzstorm.storm.event;

import zombie.ui.UIElement;

/**
 * This event fires when an {@link UIElement} is being pre-rendered.
 *
 * @see UIElement#render()
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnUIElementPreRenderEvent implements ZomboidEvent {

	public final UIElement element;

	public OnUIElementPreRenderEvent(UIElement element) {
		this.element = element;
	}

	@Override
	public String getName() {
		return "onUIElementPreRender";
	}

	@Override
	public String toString() {
		return getName();
	}
}
