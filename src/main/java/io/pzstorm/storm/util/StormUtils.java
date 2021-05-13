package io.pzstorm.storm.util;

public class StormUtils {

	/**
	 * Returns a path representation of the name for given {@code Class}.
	 *
	 * @param clazz {@code Class} whose name should be converted to path.
	 * @return {@code String} representing a path.
	 */
	public static String getClassAsPath(Class<?> clazz) {
		return clazz.getName().replace('.', '/');
	}
}
