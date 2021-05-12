package io.pzstorm.storm.mod;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Objects;
import java.util.jar.JarFile;

/**
 * This class represents an accessible and readable mod {@code jar} file.
 * Unlike {@link JarFile} it preserves the {@link File} instance pointing to jar file location.
 */
public class ModJar extends JarFile {

	/**
	 * {@code File} instance pointing to {@code jar} file location.
	 */
	private final File jarFile;

	public ModJar(File file) throws IOException {
		super(file, true);
		jarFile = file;
	}

	/**
	 * Returns {@code jar} file location in {@link URL} format so that it can be
	 * read by class loaders and other systems that read from resources. Passing the
	 * return value of this method to {@link URLClassLoader} constructor would allow that
	 * class loader to discover all classes contained in this {@code jar} file.
	 */
	public URL getResourcePath() {

		try {
			return new URL("jar:file:" + jarFile.getPath() + "!/");
		}
		catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}
		if (!(o instanceof ModJar)) {
			return false;
		}
		return jarFile.equals(((ModJar) o).jarFile);
	}

	@Override
	public int hashCode() {
		return Objects.hash(jarFile);
	}
}
