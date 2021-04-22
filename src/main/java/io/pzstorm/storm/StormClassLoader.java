import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Objects;
import java.util.Set;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
/**
 * This is a custom {@code ClassLoader} used to define, transform and load Project Zomboid classes.
 * It is initially invoked by {@link StormLauncher} when launching the game.
 */
@SuppressWarnings("WeakerAccess")
public class StormClassLoader extends ClassLoader {

	/**
	 * <p>{@code Set} of class prefixes that mark classes to be loaded with this class loader.
	 * If a class does not match this patter it's loading will be delegated to the parent loader.
	 * </p>
	 * This {@code Set} includes both Java libraries and main classes. Java Libraries have to be
	 * loaded by the same class loader loading the game so that native libraries loaded from
	 * those Java libraries become associated with the class loader loading the game.
	 * If the native libraries are loaded using a different class loader they
	 * will not be accessible to game classes.
	 */
	private static final Set<String> CLASS_WHITELIST = ImmutableSet.of(
			// zomboid library classes
			"org.lwjgl.", "net.java.games.", "jassimp.",
			// zomboid main classes
			"astar.", "com.evildevil.engines.bubble.texture.",
			"com.jcraft.", "com.sixlegs.png.", "de.jarnbjo.", "fmod.",
			"javax.vecmath.", "org.joml.", "org.luaj.kahluafork.compiler.",
			"org.mindrot.jbcrypt.", "se.krka.kahlua.", "zombie."
	);

	/**
	 * {@code ClassLoader} that is the parent of this {@code ClassLoader}.
	 * When loading classes, those classes not matching the whitelist pattern will have
	 * their loading process delegated to this {@code ClassLoader}.
	 */
	private final ClassLoader parentClassLoader;
	private final URLClassLoader urlClassLoader;

	StormClassLoader() {
		parentClassLoader = getClass().getClassLoader();
		urlClassLoader = (URLClassLoader) getParent();
	}

	/**
	 * Returns {@code true} if at least one prefix pattern in whitelist matches the given name.
	 * When a class name is considered whitelisted it will be loaded by this {@code ClassLoader}.
	 */
	@Contract("null -> fail")
	protected static boolean isWhitelistedClass(String name) {
		return CLASS_WHITELIST.stream().anyMatch(name::startsWith);
	}

	@Override
	@Contract("null -> fail")
	public @Nullable URL getResource(String name) {
		Objects.requireNonNull(name);

		URL url = urlClassLoader.getResource(name);
		if (url == null) {
			url = parentClassLoader.getResource(name);
		}
		return url;
	}

	@Override
	protected @Nullable URL findResource(String name) {
		return urlClassLoader.findResource(name);
	}
	@Override
	public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {


	@Override
	public @Nullable InputStream getResourceAsStream(String name) {

		InputStream inputStream = urlClassLoader.getResourceAsStream(name);
		if (inputStream == null) {
			inputStream = parentClassLoader.getResourceAsStream(name);
		}
		return inputStream;
	}

	/**
	 * Converts the given class name to a file name.
	 */
	@Contract(pure = true, value = "null -> fail")
	private String getClassFileName(String name) {
		return name.replace('.', '/') + ".class";
	}

	/**
	 * Reads the {@code Class} with given name into a {@code byte} array and return the result.
	 *
	 * @param name name of the {@code Class} to read.
	 * @return {@code byte} array read from {@code Class} or an empty array
	 * 		if the {@code Class} with the given name could not be found.
	 *
	 * @throws IOException if an I/O error occurred while reading or writing to stream.
	 */
	@Contract("null -> fail")
	protected byte[] getRawClassByteArray(String name) throws IOException {

		// opens an input stream to read the class for given name
		InputStream inputStream = getResourceAsStream(getClassFileName(name));
		if (inputStream == null) {
			return new byte[0];
		}
		int a = inputStream.available();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(a < 32 ? 32768 : a);
		/*
		 * read from input stream and write to output stream
		 * a maximum of 8192 bytes per write operation
		 */
		int len;
		byte[] buffer = new byte[8192];
		while ((len = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, len);
		}
		inputStream.close();
		return outputStream.toByteArray();
	}
}
