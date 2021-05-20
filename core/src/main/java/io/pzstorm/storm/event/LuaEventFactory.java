package io.pzstorm.storm.event;

import io.pzstorm.storm.event.lua.*;
import org.jetbrains.annotations.Unmodifiable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to creates new {@link LuaEvent} instances on demand.
 * The factory uses reflection to invoke the event class constructor to instantiate events.
 * Note that class and constructor instances are statically mapped and ready for access
 * which reduces overhead when using reflection to instantiate event objects.
 */
@SuppressWarnings("unchecked")
public class LuaEventFactory {

	/**
	 * This map contains {@link LuaEvent} constructors mapped to implementation classes.
	 * Constructors are mapped in this way to increase performance when instantiating {@code LuaEvent}s.
	 */
	@Unmodifiable
	private static final Map<Class<? extends LuaEvent>, Constructor<? extends LuaEvent>[]> EVENT_CONSTRUCTORS;

	/**
	 * This map contains {@link LuaEvent} classes mapped to their respected names.
	 * Classes are mapped in this way to increase performance when instantiating {@code LuaEvent}s.
	 */
	@Unmodifiable
	private static final Map<String, Class<? extends LuaEvent>> EVENT_CLASSES;

	static
	{
		Map<Class<? extends LuaEvent>, Constructor<LuaEvent>[]> eventConstructors = new HashMap<>();
		Map<String, Class<? extends LuaEvent>> eventClassesMap = new HashMap<>();
		/*
		 * this is a complete list of all event classes,
		 * if a class is not listed here it will NOT be possible
		 * to instantiate the related event instance with this factory
		 */
		Class<? extends LuaEvent>[] eventClasses = (Class<? extends LuaEvent>[]) new Class[] {
				AcceptedFactionInviteEvent.class,
				AcceptedTradeEvent.class,
				AddXPEvent.class,
				DoSpecialTooltipEvent.class,
				EveryDaysEvent.class,
				EveryHoursEvent.class,
				EveryOneMinuteEvent.class,
				EveryTenMinutesEvent.class,
				LevelPerkEvent.class,
				LoadGridsquareEvent.class,
				MngInvReceiveItemsEvent.class,
				OnAcceptInviteEvent.class,
				OnAddBuildingEvent.class,
				OnAddMessageEvent.class,
				OnAdminMessageEvent.class,
				OnAIStateChangeEvent.class,
				OnAIStateEnterEvent.class,
				OnAIStateExecuteEvent.class,
				OnAIStateExitEvent.class,
				OnAmbientSoundEvent.class,
				OnBeingHitByZombieEvent.class,
				OnCGlobalObjectSystemInitEvent.class,
				OnChallengeQueryEvent.class,
				OnChangeWeatherEvent.class,
				OnCharacterCreateStatsEvent.class,
				OnCharacterDeathEvent.class,
				OnCharacterMeetEvent.class,
				OnChatWindowInitEvent.class,
				OnClientCommandEvent.class,
				OnClimateManagerInitEvent.class,
				OnClimateTickDebugEvent.class,
				OnClimateTickEvent.class,
				OnClothingUpdatedEvent.class,
				OnConnectionStateChangedEvent.class,
				OnConnectedEvent.class,
				OnConnectFailedEvent.class,
				OnCharacterCollideEvent.class,
				OnCreateLivingCharacterEvent.class,
				OnFETickEvent.class,
				OnFillInventoryObjectContextMenuEvent.class,
				OnFillWorldObjectContextMenuEvent.class,
				OnGameBootEvent.class,
				OnGameStartEvent.class,
				OnJoypadActivateEvent.class,
				OnKeyPressedEvent.class,
				OnKeyStartPressedEvent.class,
				OnLoadMapZonesEvent.class,
				OnMultiTriggerNPCEvent.class,
				OnObjectCollideEvent.class,
				OnPlayerUpdateEvent.class,
				OnPostUIDrawEvent.class,
				OnPreFillInventoryObjectContextMenuEvent.class,
				OnPreFillWorldObjectContextMenuEvent.class,
				OnPreUIDrawEvent.class,
				OnRefreshInventoryWindowContainersEvent.class,
				OnTickEvenPausedEvent.class,
				OnTickEvent.class,
				OnTriggerNPCEvent.class,
				OnWeaponHitCharacterEvent.class,
				OnWeaponSwingEvent.class,
				OnZombieUpdateEvent.class
		};
		for (Class<? extends LuaEvent> eventClass : eventClasses)
		{
			Constructor<?>[] constructors = eventClass.getConstructors();
			for (Constructor<?> constructor : constructors)
			{
				if (!Modifier.isPublic(constructor.getModifiers())) {
					throw new IllegalStateException("Found inaccessible constructor for class '" + eventClass + '\'');
				}
			}
			eventConstructors.put(eventClass, (Constructor<LuaEvent>[]) constructors);

			String className = getEventName(eventClass);
			eventClassesMap.put(className, eventClass);
		}
		EVENT_CONSTRUCTORS = Collections.unmodifiableMap(eventConstructors);
		EVENT_CLASSES = Collections.unmodifiableMap(eventClassesMap);
	}

	/**
	 * Construct a new instance of {@link LuaEvent} with given array of arguments.
	 *
	 * @param eventClass {@code Class} denoting the {@code LuaEvent} to construct.
	 * @param args array of arguments to use when instantiating {@code LuaEvent}.
	 *
	 * @throws IllegalStateException if an error occurred while instantiating {@code LuaEvent}
	 * 		or no registered constructors found for given event class.
	 * @throws IllegalArgumentException if no constructor with parameters matching specified array of
	 * 		arguments was found for given {@code LuaEvent} class.
	 *
	 * @return new instance of {@code LuaEvent} for given class.
	 */
	public static LuaEvent constructLuaEvent(Class<? extends LuaEvent> eventClass, Object... args) {

		Constructor<?>[] constructors = EVENT_CONSTRUCTORS.get(eventClass);
		if (constructors != null)
		{
			Class<?>[] argTypes = new Class<?>[args.length];
			for (int i = 0; i < args.length; i++) {
				argTypes[i] = args[i] != null ? args[i].getClass() : null;
			}
			for (Constructor<?> constructor : constructors)
			{
				if (constructor.getParameterCount() == args.length)
				{
					Class<?>[] paramTypes = constructor.getParameterTypes();
					for (int i = 0; i < paramTypes.length; i++)
					{
						// on parameter type mismatch skip to next constructor
						if (paramTypes[i] != argTypes[i]) {
							break;
						}
					}
					try {
						// if all parameter types match return this constructor
						return (LuaEvent) constructor.newInstance(args);
					}
					catch (ReflectiveOperationException e) {
						throw new IllegalStateException(e);
					}
				}
			}
			String message = "Unable to find constructor for class '%s' that matches arguments %s";
			throw new IllegalArgumentException(String.format(message, eventClass, Arrays.toString(args)));
		}
		throw new IllegalStateException("No registered constructors found for event '" + eventClass + '\'');
	}

	/**
	 * Construct a new instance of {@link LuaEvent} with given array of arguments.
	 *
	 * @param eventName name of the {@code Class} denoting the {@code LuaEvent} to construct.
	 * @param args array of arguments to use when instantiating {@code LuaEvent}.
	 *
	 * @throws IllegalStateException if no registered {@code LuaEvent} class found for given name
	 * 		or an error occurred while instantiating {@code LuaEvent} or no registered
	 * 		constructors found for event class resolved from given name.
	 * @throws IllegalArgumentException if no constructor with parameters matching specified array of
	 * 		arguments was found for {@code LuaEvent} class resolved from given name.
	 *
	 * @return new instance of {@code LuaEvent} for class of given name.
	 */
	public static LuaEvent constructLuaEvent(String eventName, Object... args) {

		Class<? extends LuaEvent> eventClass = EVENT_CLASSES.get(eventName);
		if (eventClass == null) {
			throw new IllegalStateException("No registered LuaEvent class found for name '" + eventName + '\'');
		}
		return constructLuaEvent(eventClass, args);
	}

	/**
	 * Resolve and return name of the event denoted by given {@code Class}.
	 */
	static String getEventName(Class<? extends LuaEvent> eventClass) {

		String className = eventClass.getSimpleName();
		return className.endsWith("Event") ? className.substring(0, className.length() - 5) : className;
	}
}
