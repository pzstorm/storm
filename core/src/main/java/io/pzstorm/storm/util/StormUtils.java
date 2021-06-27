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

package io.pzstorm.storm.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import io.pzstorm.storm.core.StormClassLoader;
import zombie.core.textures.Texture;

public class StormUtils {

	/**
	 * Returns a path representation of the name for given {@code Class}.
	 *
	 * @param clazz {@code Class} whose name should be converted to path.
	 * @return {@code String} representing a path.
	 */
	@Contract(pure = true)
	public static String getClassAsPath(Class<?> clazz) {
		return clazz.getName().replace('.', '/');
	}

	/**
	 * Get game texture by reading the resource with specified name from stream.
	 *
	 * @param name the resource name.
	 * @param classLoader {@code ClassLoader} used to search the resource.
	 *
	 * @throws IOException if an error occurred while decoding the texture image.
	 * @see StormClassLoader#getResourceAsStream(String)
	 */
	public static @Nullable Texture getTextureResourceFromStream(String name, ClassLoader classLoader) throws IOException {

		Objects.requireNonNull(name);
		try {
			InputStream resource = classLoader.getResourceAsStream(name);
			return resource != null ? new Texture(name, new BufferedInputStream(resource), false) : null;
		}
		catch (Exception e) {
			throw new IOException(e);
		}
	}
}
