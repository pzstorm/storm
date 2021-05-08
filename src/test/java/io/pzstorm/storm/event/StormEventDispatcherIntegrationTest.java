package io.pzstorm.storm.event;

import io.pzstorm.storm.IntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("unused")
class StormEventDispatcherIntegrationTest implements IntegrationTest {

	@Test
	void shouldRegisterEventHandlerWithInstanceContextAndDispatchEvents() {

		TestInstanceContextEventHandler handler = new TestInstanceContextEventHandler();
		StormEventDispatcher.registerEventHandler(handler);

		Assertions.assertFalse(handler.eventsCalled[0]);
		StormEventDispatcher.dispatchEvent(new TestZomboidEventA());
		Assertions.assertTrue(handler.eventsCalled[0]);

		Assertions.assertFalse(handler.eventsCalled[1]);
		StormEventDispatcher.dispatchEvent(new TestZomboidEventB());
		Assertions.assertTrue(handler.eventsCalled[1]);
	}

	@Test
	void shouldRegisterEventHandlerWithStaticContextAndDispatchEvents() {

		StormEventDispatcher.registerEventHandler(TestStaticContextEventHandler.class);

		Assertions.assertFalse(TestStaticContextEventHandler.eventsCalled[0]);
		StormEventDispatcher.dispatchEvent(new TestZomboidEventA());
		Assertions.assertTrue(TestStaticContextEventHandler.eventsCalled[0]);

		Assertions.assertFalse(TestStaticContextEventHandler.eventsCalled[1]);
		StormEventDispatcher.dispatchEvent(new TestZomboidEventB());
		Assertions.assertTrue(TestStaticContextEventHandler.eventsCalled[1]);
	}

	@Test
	void shouldThrowExceptionWhenHandlerRegisteredInWrongContext() {

		// registration of instance context
		Assertions.assertThrows(IllegalArgumentException.class, () ->
				StormEventDispatcher.registerEventHandler(TestInstanceContextEventHandler.class)
		);
		// registration of static context
		//noinspection InstantiationOfUtilityClass
		Assertions.assertThrows(IllegalArgumentException.class, () ->
				StormEventDispatcher.registerEventHandler(new TestStaticContextEventHandler())
		);
		// completely valid method
		Assertions.assertDoesNotThrow(() ->
				StormEventDispatcher.registerEventHandler(new Object() {
					@SubscribeEvent
					public void handleEvent(ZomboidEvent event) {}
				})
		);
	}

	@Test
	void shouldThrowExceptionWhenHandlerRegisteredWithInvalidMethodParameters() {

		// missing method parameters
		Assertions.assertThrows(IllegalArgumentException.class, () ->
				StormEventDispatcher.registerEventHandler(new Object() {
					@SuppressWarnings("unused")
					@SubscribeEvent
					public void handleEvent() {}
				})
		);
		// invalid method parameter type
		Assertions.assertThrows(IllegalArgumentException.class, () ->
				StormEventDispatcher.registerEventHandler(new Object() {
					@SubscribeEvent
					public void handleEvent(Object object) {}
				})
		);
		// extra method parameters
		Assertions.assertThrows(IllegalArgumentException.class, () ->
				StormEventDispatcher.registerEventHandler(new Object() {
					@SubscribeEvent
					public void handleEvent(ZomboidEvent event, Object object) {}
				})
		);
		// completely valid method
		Assertions.assertDoesNotThrow(() ->
				StormEventDispatcher.registerEventHandler(new Object() {
					@SubscribeEvent
					public void handleEvent(ZomboidEvent event) {}
				})
		);
	}
}
