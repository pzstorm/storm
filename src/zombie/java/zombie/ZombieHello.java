package zombie;

import org.jetbrains.annotations.TestOnly;

import io.pzstorm.storm.StormLogger;

@TestOnly
@SuppressWarnings({ "unused", "WeakerAccess" })
public class ZombieHello {

	public static void sayHello() {
		StormLogger.info(getHello());
	}

	public static String getHello() {
		return "Zombie says: hello!";
	}
}
