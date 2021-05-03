package zombie;

import io.pzstorm.storm.StormLogger;
import org.jetbrains.annotations.TestOnly;


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
