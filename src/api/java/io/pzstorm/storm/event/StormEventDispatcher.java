package io.pzstorm.storm.event;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;

import com.google.common.collect.Sets;

import io.pzstorm.storm.logging.StormLogger;

/**
 * This class is responsible for registering event handlers and dispatching {@link ZomboidEvent} instances.
 * <p>
 * To register an event handler call one of the following methods:
 * </p><ul>
 * <li>{@link #registerEventHandler(Object)} - when subscribed methods are instance methods.</li>
 * <li>{@link #registerEventHandler(Class)} - when subscribed methods are static methods.</li>
 * </ul>
 * All methods in registered event handlers annotated with {@link SubscribeEvent} will be called
 * by {@link #dispatchEvent(ZomboidEvent)} method when an appropriate event is created by installed
 * {@code StormHook}. Each subscribed method has to have exactly one parameter that matches the type
 * of event it wants to subscribe to. For example if a method wanted to subscribe to {@code OnRenderEvent}
 * it would define itself in one of two ways depending on the handler registration method used:
 * <br><br><pre>
 *     // handler must be registered as a class
 *     public static void handleRenderEvent(OnRenderEvent event) {
 *         ...
 *     }
 *     // handler must be registered as an instance
 *     public void handleRenderEvent(OnRenderEvent event) {
 *         ...
 *     }
 * </pre>
 * Do not mix static and instance subscribed methods. A registered handler has to have all
 * subscribed methods declared as either static or instance methods depending on the method used
 * to register the handler.
 */
@SuppressWarnings({ "unused", "WeakerAccess" })
public class StormEventDispatcher {

	/**
	 * Internal registry that maps {@link ZomboidEvent} classes to handler methods.
	 * These methods are then invoked when dispatching matching events. Event registration happens
	 * on-demand which means that registry will only contain an event entry if at least one registered
	 * handler contains at least one method that subscribe to that event.
	 */
	private static final Map<Class<? extends ZomboidEvent>, Set<EventHandlerMethod>> DISPATCH_REGISTRY = new HashMap<>();

	/**
	 * Internally register given method for specified event handler.
	 *
	 * @param method {@code Method} to register with event handler.
	 * @param handler event handler to register along with {@code Method}. It can be either an
	 * 		instance of an object or a {@code Class} that represents the handler.
	 *
	 * @throws IllegalArgumentException if the handler parameter is {@code null} and given method is
	 * 		<i>not</i> declared as {@code static}, handler is <i>not</i> {@code null} and given method is
	 * 		declared as {@code static}, if the given {@code Method} does not have exactly one argument
	 * 		or the argument is not an instance of {@link ZomboidEvent}.
	 */
	@SuppressWarnings("unchecked")
	private static void registerEventHandlerMethod(Method method, @Nullable Object handler) {

		if (method.isAnnotationPresent(SubscribeEvent.class))
		{
			Class<?>[] parameters = method.getParameterTypes();
			if (parameters.length == 1)
			{
				Class<?> cEventClass = parameters[0];
				if (ZomboidEvent.class.isAssignableFrom(cEventClass))
				{
					if (handler == null)
					{
						if (!Modifier.isStatic(method.getModifiers()))
						{
							throw new IllegalArgumentException("Tried to register INSTANCE event handler method " +
									"by passing null event handler. Either make the method STATIC or use a " +
									"different context to register the handler. See StormEventDispatcher class " +
									"documentation for more information. Method: " + method.getName());
						}
					}
					else if (Modifier.isStatic(method.getModifiers()))
					{
						throw new IllegalArgumentException("Tried to register STATIC event handler method " +
								"by passing an instance of handler class. Either remove the STATIC modifier " +
								"or use a different context to register the handler. See StormEventDispatcher " +
								"class documentation for more information. Method: " + method.getName());
					}
					Class<? extends ZomboidEvent> eventClass = (Class<? extends ZomboidEvent>) cEventClass;
					EventHandlerMethod eventHandlerMethod = new EventHandlerMethod(method, handler);

					Set<EventHandlerMethod> handlerMethods = DISPATCH_REGISTRY.get(eventClass);
					if (handlerMethods == null) {
						DISPATCH_REGISTRY.put(eventClass, Sets.newHashSet(eventHandlerMethod));
					}
					else handlerMethods.add(eventHandlerMethod);
				}
				else {
					String className = handler instanceof Class ? ((Class<?>) handler).getName() :
							(handler != null ? handler.getClass().getName() : "null");

					String text = "Invalid arguments for method %s(%s). Expected ZomboidEvent but found %s";
					throw new IllegalArgumentException(String.format(
							text, method.getName(), className, cEventClass.getName()));
				}
			}
			else {
				String className = handler instanceof Class ? ((Class<?>) handler).getName() :
						(handler != null ? handler.getClass().getName() : "null");

				String text = "Invalid arguments for method %s(%s). Expected exactly one argument but found %d";
				throw new IllegalArgumentException(String.format(text, method.getName(), className, parameters.length));
			}
		}
	}

	/**
	 * Register all <b>static</b> methods subscribed with {@link SubscribeEvent} annotation in the given
	 * {@code Class} to dispatch registry. The registered methods will then be called by dispatched whenever
	 * an event they are subscribed to fires. Note that the methods have to be properly defined see
	 * {@link StormEventDispatcher} class documentation for more information.
	 *
	 * @param handlerClass {@code Class} of the event handler to register.
	 *
	 * @throws IllegalArgumentException if any subscribing method declared in handler is <i>not</i> declared as
	 *        {@code static}, if the any subscribing method does not have exactly one argument
	 * 		or the argument is not an instance of {@link ZomboidEvent}.
	 * @see #registerEventHandler(Object)
	 */
	public static void registerEventHandler(Class<?> handlerClass) {

		StormLogger.debug("Registering event handler for class " + handlerClass.getName());
		for (Method method : handlerClass.getMethods()) {
			registerEventHandlerMethod(method, null);
		}
	}

	/**
	 * Register all <b>instance</b> methods subscribed with {@link SubscribeEvent} annotation in the given
	 * object instance to dispatch registry. The registered methods will then be called by dispatched whenever
	 * an event they are subscribed to fires. Note that the methods have to be properly defined see
	 * {@link StormEventDispatcher} class documentation for more information.
	 *
	 * @param handler instance of the event handler to register.
	 *
	 * @throws IllegalArgumentException if any subscribing method declared in handler is declared as
	 *        {@code static}, if the any subscribing method does not have exactly one argument
	 * 		or the argument is not an instance of {@link ZomboidEvent}.
	 * @see #registerEventHandler(Class)
	 */
	public static void registerEventHandler(Object handler) {

		StormLogger.debug("Registering event handler for instance of class " + handler.getClass().getName());
		for (Method method : handler.getClass().getMethods()) {
			registerEventHandlerMethod(method, handler);
		}
	}

	/**
	 * Create and return a list of instructions that calls {@link #dispatchEvent(ZomboidEvent)} method.
	 * This is a convenience method intended to be used <b>only</b> by {@code StormHook} implementations to
	 * get a list of instructions that represents a dispatch call for given event.
	 *
	 * @param eventConstructorInsn list of instructions that represent constructing a new
	 *        {@link ZomboidEvent} instance and adding the result to the stack. These instructions
	 * 		will be <i>transferred</i> to the start of the resulting instruction list.
	 *
	 * @see #dispatchEvent(ZomboidEvent)
	 */
	public static InsnList callDispatchEvent(List<AbstractInsnNode> eventConstructorInsn) {

		InsnList result = new InsnList();
		if (!LabelNode.class.isAssignableFrom(eventConstructorInsn.get(0).getClass())) {
			result.add(new LabelNode());
		}
		for (AbstractInsnNode constructorInsn : eventConstructorInsn) {
			result.add(constructorInsn);
		}
		result.add(new MethodInsnNode(
				Opcodes.INVOKESTATIC, "io/pzstorm/storm/event/StormEventDispatcher",
				"dispatchEvent", "(Lio/pzstorm/storm/event/ZomboidEvent;)V"
		));
		return result;
	}

	/**
	 * Dispatch the given event to all methods registered in dispatch registry. This is an internal
	 * method <b>only</b> called by {@code StormHook} implementations installed in game code.
	 *
	 * @param event {@link ZomboidEvent} to dispatch.
	 * @see #callDispatchEvent(List)
	 */
	public static void dispatchEvent(ZomboidEvent event) {

		Set<EventHandlerMethod> handlerMethods = DISPATCH_REGISTRY.get(event.getClass());
		if (handlerMethods != null)
		{
			for (EventHandlerMethod method : handlerMethods) {
				method.invoke(event);
			}
		}
	}

	private static class EventHandlerMethod {

		private final Method method;
		private final @Nullable Object handler;

		private EventHandlerMethod(Method method, @Nullable Object handler) {

			this.method = method;
			this.handler = handler;
		}

		private void invoke(ZomboidEvent event) {

			try {
				method.invoke(handler, event);
			}
			catch (ReflectiveOperationException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
