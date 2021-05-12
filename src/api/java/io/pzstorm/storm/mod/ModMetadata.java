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
