package zombie;

import org.jetbrains.annotations.TestOnly;

@TestOnly
public class ZombieHello {

	public static void sayHello() {
		System.out.println(getHello());
	}

	public static String getHello() {
		return "Zombie says: hello!";
	}
}
