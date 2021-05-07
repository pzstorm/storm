package zombie;

import org.jetbrains.annotations.TestOnly;

@TestOnly
@SuppressWarnings({ "unused", "WeakerAccess" })
public class ZombieHello {

	@SuppressWarnings("UseOfSystemOutOrSystemErr")
	public static void sayHello() {
		System.out.println(getHello());
	}

	public static String getHello() {
		return "Zombie says: hello!";
	}
}
