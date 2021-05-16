package io.pzstorm.storm.mod;

import java.util.Objects;

/**
 * This class represents mod version that adheres to standard mod version scheme.
 */
public class ModVersion {

	// TODO: implement semantic versioning
	private final String version;

	public ModVersion(String version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}
		if (!(o instanceof ModVersion)) {
			return false;
		}
		return version.equals(((ModVersion) o).version);
	}

	@Override
	public int hashCode() {
		return Objects.hash(version);
	}

	@Override
	public String toString() {
		return version;
	}
}
