package io.pzstorm.storm.mod;

/**
 * This class represents a Project Zomboid Java mod entry point. Every mod is expected to have
 * a single class that implements this class. Mods that do not implement this class will not be
 * registered and will not be eligible to subscribe to events.
 */
public interface ZomboidMod {
	void registerEventHandlers();
}
