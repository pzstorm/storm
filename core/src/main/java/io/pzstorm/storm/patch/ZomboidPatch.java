package io.pzstorm.storm.patch;

import io.pzstorm.storm.core.StormClassTransformer;

/**
 * <p>This class represents a Project Zomboid {@code Class} code patch.
 * A code patch is a series of instructions that are introduced with ASM class transformation
 * to remove and/or changes code lines. Patch can also add new lines of code but unlike hooks it
 * also removes or changes existing code lines which makes it a much more intrusive.</p>
 * <p>Patches should be applied only in situations where new functionality (which cannot
 * be implemented with hooks) is being introduced to the game engine.</p>
 */
public interface ZomboidPatch {

	/**
	 * Apply a code patch with the given {@link StormClassTransformer}.
	 */
	void applyPatch(StormClassTransformer transformer);
}
