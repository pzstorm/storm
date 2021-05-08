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

}
