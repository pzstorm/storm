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

import zombie.characters.IsoGameCharacter;

/**
 * Triggers when a vehicle mechanic action has been completed.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class OnMechanicActionDoneEvent implements LuaEvent {

	// TODO: finish documenting this event
	public final IsoGameCharacter player;
	public final Boolean var1;
	public final Integer var2;
	public final String var3;
	public final Long var4;
	public final Boolean var5;

	public OnMechanicActionDoneEvent(IsoGameCharacter player, Boolean var1,
									 Integer var2, String var3, Long var4, Boolean var5) {

		this.player = player;
		this.var1 = var1;
		this.var2 = var2;
		this.var3 = var3;
		this.var4 = var4;
		this.var5 = var5;
	}
}
