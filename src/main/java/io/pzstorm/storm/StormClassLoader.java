import java.net.URLClassLoader;
import org.jetbrains.annotations.Contract;
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
	public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
	}
}
