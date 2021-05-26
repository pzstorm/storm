package zombie;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This class is used to mark dummy Zomboid classes that are used instead of real Zomboid classes
 * when compiling Java on CI server. Without these classes the compile task will fail on CI.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface ZomboidClass {

}
