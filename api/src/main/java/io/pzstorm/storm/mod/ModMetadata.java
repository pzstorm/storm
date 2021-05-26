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

package io.pzstorm.storm.mod;

import java.util.Objects;

/**
 * This class represents mod metadata stored in {@code mod.info} file in mod project root directory.
 */
public class ModMetadata {

	/**
	 * Mod name as defined in {@code mod.info} file with {@code name} property.
	 */
	public final String name;

	/**
	 * Mod version as defined in {@code mod.info} file with {@code modversion} property.
	 */
	public final ModVersion version;

	public ModMetadata(String name, ModVersion version) {

		this.name = name;
		this.version = version;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}
		if (!(o instanceof ModMetadata)) {
			return false;
		}
		ModMetadata that = (ModMetadata) o;
		return name.equals(that.name) && version.equals(that.version);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, version);
	}

	@Override
	public String toString() {
		return name + " (" + version + ')';
	}
}
