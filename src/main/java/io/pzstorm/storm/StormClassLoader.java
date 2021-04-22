import java.net.URLClassLoader;
/**
 * This is a custom {@code ClassLoader} used to define, transform and load Project Zomboid classes.
 * It is initially invoked by {@link StormLauncher} when launching the game.
 */
@SuppressWarnings("WeakerAccess")
public class StormClassLoader extends ClassLoader {
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
	@Override
	public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
	}
}
