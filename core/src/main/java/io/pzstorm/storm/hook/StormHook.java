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

package io.pzstorm.storm.hook;

import io.pzstorm.storm.core.StormClassTransformer;
import io.pzstorm.storm.event.StormEventDispatcher;
import io.pzstorm.storm.event.ZomboidEvent;

/**
 * This interface represents a hook in game code that creates an instance of {@link ZomboidEvent}
 * and calls {@link StormEventDispatcher}. The dispatcher then forwards the event to all registered
 * methods that have subscribed to event in context. Hooks are only intended for <b>internal</b> use
 * to generate and send events from game code.
 *
 * @see StormEventDispatcher#dispatchEvent(ZomboidEvent)
 */
public interface StormHook {

	/**
	 * Use the given transformer to install {@link StormHook} in game code. The installation is
	 * done by altering method bytecode with the use of ASM to create an instance of {@link ZomboidEvent}
	 * and create a direct callback to {@link StormEventDispatcher} class. Note that the transformers
	 * used to install hooks need to be registered in the static block of {@link StormClassTransformer}.
	 *
	 * @param transformer {@link StormClassTransformer} to use to install the hook.
	 */
	void installHook(StormClassTransformer transformer);
}
