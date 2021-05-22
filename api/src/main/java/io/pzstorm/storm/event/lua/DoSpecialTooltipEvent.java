/*
 * Zomboid Storm - Java modding toolchain for Project Zomboid
 * Copyright (C) 2021 Matthew Cain
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package io.pzstorm.storm.event.lua;

import zombie.iso.IsoGridSquare;
import zombie.iso.IsoObject;
import zombie.ui.ObjectTooltip;

/**
 * This event triggers when {@link IsoObject} renders special tooltip.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class DoSpecialTooltipEvent implements LuaEvent {

	/**
	 * The tooltip being rendered.
	 */
	public final ObjectTooltip tooltip;

	/**
	 * Location of {@link IsoObject} being rendered.
	 */
	public final IsoGridSquare square;

	public DoSpecialTooltipEvent(ObjectTooltip tooltip, IsoGridSquare square) {

		this.tooltip = tooltip;
		this.square = square;
	}
}
