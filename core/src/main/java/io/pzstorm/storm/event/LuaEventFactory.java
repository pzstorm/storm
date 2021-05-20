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
				OnGameBootEvent.class,
				OnPreGameStartEvent.class,
				OnTickEvent.class,
				OnTickEvenPausedEvent.class,
				OnRenderUpdateEvent.class,
				OnFETickEvent.class,
				OnGameStartEvent.class,
				OnPreUIDrawEvent.class,
				OnPostUIDrawEvent.class,
				OnCharacterCollideEvent.class,
				OnKeyStartPressedEvent.class,
				OnKeyPressedEvent.class,
				OnObjectCollideEvent.class,
				OnNPCSurvivorUpdateEvent.class,
				OnPlayerUpdateEvent.class,
				OnZombieUpdateEvent.class,
				OnTriggerNPCEvent.class,
				OnMultiTriggerNPCEvent.class,
				OnLoadMapZonesEvent.class,
				OnAddBuildingEvent.class,
				OnCreateLivingCharacterEvent.class,
				OnChallengeQueryEvent.class,
				OnFillInventoryObjectContextMenuEvent.class,
				OnPreFillInventoryObjectContextMenuEvent.class,
				OnFillWorldObjectContextMenuEvent.class,
				OnPreFillWorldObjectContextMenuEvent.class,
				OnRefreshInventoryWindowContainersEvent.class,
				OnMakeItemEvent.class,
				OnJoypadActivateEvent.class,
				OnWeaponHitCharacterEvent.class,
				OnWeaponSwingEvent.class,
				OnWeaponHitTreeEvent.class,
				OnWeaponHitXpEvent.class,
				OnWeaponSwingHitPointEvent.class,
				OnPlayerAttackFinishedEvent.class,
				OnLoginStateEvent.class,
				OnLoginStateSuccessEvent.class,
				OnCharacterCreateStatsEvent.class,
				OnLoadSoundBanksEvent.class,
				OnObjectLeftMouseButtonDownEvent.class,
				OnObjectLeftMouseButtonUpEvent.class,
				OnObjectRightMouseButtonDownEvent.class,
				OnObjectRightMouseButtonUpEvent.class,
				OnDoTileBuildingEvent.class,
				OnDoTileBuilding2Event.class,
				OnDoTileBuilding3Event.class,
				OnConnectFailedEvent.class,
				OnConnectedEvent.class,
				OnDisconnectEvent.class,
				OnConnectionStateChangedEvent.class,
				OnScoreboardUpdateEvent.class,
				OnMouseMoveEvent.class,
				OnMouseDownEvent.class,
				OnMouseUpEvent.class,
				OnRightMouseDownEvent.class,
				OnRightMouseUpEvent.class,
				OnNewSurvivorGroupEvent.class,
				OnPlayerSetSafehouseEvent.class,
				OnLoadEvent.class,
				AddXPEvent.class,
				LevelPerkEvent.class,
				OnSaveEvent.class,
				OnMainMenuEnterEvent.class,
				OnPreMapLoadEvent.class,
				OnPostFloorSquareDrawEvent.class,
				OnPostFloorLayerDrawEvent.class,
				OnPostTilesSquareDrawEvent.class,
				OnPostTileDrawEvent.class,
				OnPostWallSquareDrawEvent.class,
				OnPostCharactersSquareDrawEvent.class,
				OnCreateUIEvent.class,
				OnMapLoadCreateIsoObjectEvent.class,
				OnCreateSurvivorEvent.class,
				OnCreatePlayerEvent.class,
				OnPlayerDeathEvent.class,
				OnZombieDeadEvent.class,
				OnCharacterDeathEvent.class,
				OnCharacterMeetEvent.class,
				OnSpawnRegionsLoadedEvent.class,
				OnPostMapLoadEvent.class,
				OnAIStateExecuteEvent.class,
				OnAIStateEnterEvent.class,
				OnAIStateExitEvent.class,
				OnAIStateChangeEvent.class,
				OnPlayerMoveEvent.class,
				OnInitWorldEvent.class,
				OnNewGameEvent.class,
				OnIsoThumpableLoadEvent.class,
				OnIsoThumpableSaveEvent.class,
				ReuseGridsquareEvent.class,
				LoadGridsquareEvent.class,
				EveryOneMinuteEvent.class,
				EveryTenMinutesEvent.class,
				EveryDaysEvent.class,
				EveryHoursEvent.class,
				OnDuskEvent.class,
				OnDawnEvent.class,
				OnEquipPrimaryEvent.class,
				OnEquipSecondaryEvent.class,
				OnClothingUpdatedEvent.class,
				OnRainStartEvent.class,
				OnRainStopEvent.class,
				OnAmbientSoundEvent.class,
				OnResetLuaEvent.class,
				OnModsModifiedEvent.class,
				OnSeeNewRoomEvent.class,
				OnNewFireEvent.class,
				OnFillContainerEvent.class,
				OnChangeWeatherEvent.class,
				OnRenderTickEvent.class,
				OnJoypadActivateUIEvent.class,
				OnDestroyIsoThumpableEvent.class,
				OnPostSaveEvent.class,
				OnResolutionChangeEvent.class,
				OnWaterAmountChangeEvent.class,
				OnClientCommandEvent.class,
				OnServerCommandEvent.class,
				OnContainerUpdateEvent.class,
				OnObjectAddedEvent.class,
				OnObjectAboutToBeRemovedEvent.class,
				onLoadModDataFromServerEvent.class,
				OnGameTimeLoadedEvent.class,
				OnCGlobalObjectSystemInitEvent.class,
				OnSGlobalObjectSystemInitEvent.class,
				OnWorldMessageEvent.class,
				OnKeyKeepPressedEvent.class,
				SendCustomModDataEvent.class,
				ServerPingedEvent.class,
				OnServerStartedEvent.class,
				OnLoadedTileDefinitionsEvent.class,
				OnPostRenderEvent.class,
				DoSpecialTooltipEvent.class,
				OnCoopJoinFailedEvent.class,
				OnServerWorkshopItemsEvent.class,
				OnVehicleDamageTextureEvent.class,
				OnCustomUIKeyEvent.class,
				OnCustomUIKeyPressedEvent.class,
				OnCustomUIKeyReleasedEvent.class,
				OnDeviceTextEvent.class,
				OnRadioInteractionEvent.class,
				OnLoadRadioScriptsEvent.class,
				OnAcceptInviteEvent.class,
				OnCoopServerMessageEvent.class,
				OnReceiveUserlogEvent.class,
				OnAdminMessageEvent.class,
				OnGetDBSchemaEvent.class,
				OnGetTableResultEvent.class,
				ReceiveFactionInviteEvent.class,
				AcceptedFactionInviteEvent.class,
				ViewTicketsEvent.class,
				SyncFactionEvent.class,
				OnReceiveItemListNetEvent.class,
				OnMiniScoreboardUpdateEvent.class,
				OnSafehousesChangedEvent.class,
				RequestTradeEvent.class,
				AcceptedTradeEvent.class,
				TradingUIAddItemEvent.class,
				TradingUIRemoveItemEvent.class,
				TradingUIUpdateStateEvent.class,
				OnGridBurntEvent.class,
				OnPreDistributionMergeEvent.class,
				OnDistributionMergeEvent.class,
				OnPostDistributionMergeEvent.class,
				MngInvReceiveItemsEvent.class,
				OnTileRemovedEvent.class,
				OnServerStartSavingEvent.class,
				OnServerFinishSavingEvent.class,
				OnMechanicActionDoneEvent.class,
				OnClimateTickEvent.class,
				OnThunderEvent.class,
				OnEnterVehicleEvent.class,
				OnSteamGameJoinEvent.class,
				OnTabAddedEvent.class,
				OnSetDefaultTabEvent.class,
				OnTabRemovedEvent.class,
				OnAddMessageEvent.class,
				SwitchChatStreamEvent.class,
				OnChatWindowInitEvent.class,
				OnInitSeasonsEvent.class,
				OnClimateTickDebugEvent.class,
				OnInitModdedWeatherStageEvent.class,
				OnUpdateModdedWeatherStageEvent.class,
				OnClimateManagerInitEvent.class,
				OnPressReloadButtonEvent.class,
				OnPressRackButtonEvent.class,
				OnHitZombieEvent.class,
				OnBeingHitByZombieEvent.class
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
