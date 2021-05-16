package io.pzstorm.storm.core;

/**
 * Signals that an unexpected error occurred while transforming classes.
 * This exception is only meant to be used by {@link StormClassTransformer} classes.
 */
class ClassTransformationException extends RuntimeException {

	ClassTransformationException(String message, Throwable cause) {
		super(message, cause);
	}

	ClassTransformationException(String message) {
		super(message);
	}
}
