package zombie;

@SuppressWarnings({ "WeakerAccess", "unused" })
public class ZombieUtils {

	public static int zombiePropertyA = 0;
	public static String zombiePropertyB = "";
	public static boolean zombiePropertyC = false;

	public static void setZombieProperties(int a, boolean c) {

		zombiePropertyA = a;

		// inject instruction to set zombiePropertyB here
		//zombiePropertyB = "property";

		zombiePropertyC = c;
	}
}
