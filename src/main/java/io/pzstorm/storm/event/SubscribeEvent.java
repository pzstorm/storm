package io.pzstorm.storm.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to subscribe methods to {@link ZomboidEvent} types. Methods are registered
 * in bulk via event handler classes. To register an event handler call an appropriate class in
 * {@link StormEventDispatcher}. Once the event handler has been registered all methods belonging to
 * the event handler annotated with {@link SubscribeEvent} will be subscribed to events they specify
 * as method parameters. Read more about this process in {@link StormEventDispatcher} class documentation.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@SuppressWarnings("WeakerAccess")
public @interface SubscribeEvent {
}
