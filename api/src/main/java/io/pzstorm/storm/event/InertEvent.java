package io.pzstorm.storm.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.pzstorm.storm.event.lua.LuaEvent;

/**
 * This annotation indicates that the annotated {@link LuaEvent} is never triggered
 * from game code. Note that this annotation is only used to document classes and
 * has no effect other then documenting source code.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface InertEvent {

}
