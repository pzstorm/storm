package io.pzstorm.storm.core;

import java.util.Optional;

import io.pzstorm.storm.logging.StormLogger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;

/**
 * This class represents a {@code Class} transformer used to alter {@code Class}
 * bytecode using ASM. The transformation process calls the following method in fixed order:
 * <ul>
 * <li>{@link #read(byte[])} to read given byte array with {@link ClassReader}.</li>
 * <li>{@link #visit()} to use the visitation system to parse the class with visitor.</li>
 * <li>{@link #transform()} to transform the visited class before writing it.</li>
 * <li>{@link #write()} to write the class using {@link ClassWriter}</li>
 * </ul>
 */
public abstract class StormClassTransformer {

	final String className;
	final ClassNode visitor;
	private @Nullable ClassReader classReader;

	StormClassTransformer(String className, ClassNode visitor) {

		this.className = className;
		this.visitor = visitor;
	}

	StormClassTransformer(String className) {
		this(className, new ClassNode());
	}

	/**
	 * Returns list of instructions for method that matches given parameters.
	 *
	 * @param name name of the method to match.
	 * @param descriptor descriptor of the method to match.
	 *
	 * @return list of instructions for matched method or an empty {@link InsnList}.
	 *
	 * @see <a href="https://asm.ow2.io/asm4-guide.pdf#subsection.2.1.4">
	 * 		ASM User Guide - Method descriptors</a>
	 */
	@Contract(pure = true)
	public final InsnList getInstructionsForMethod(String name, String descriptor) {

		if (classReader != null)
		{
			Optional<MethodNode> methodNode = visitor.methods.stream()
					.filter(m -> m.name.equals(name) && m.desc.equals(descriptor)).findFirst();

			if (methodNode.isPresent()) {
				return methodNode.get().instructions;
			}
		}
		return new InsnList();
	}

	/**
	 * Read or parse class byte array using {@link ClassReader}.
	 *
	 * @param rawClass array of bytes to read.
	 *
	 * @return {@code this} instance of {@link StormClassTransformer}.
	 *
	 * @throws ClassTransformationException if given byte array is empty.
	 * @see <a href="https://asm.ow2.io/asm4-guide.pdf#subsection.2.2.2">
	 * 		ASM User Guide - Parsing classes</a>
	 */
	@Contract("_ -> this")
	StormClassTransformer read(byte[] rawClass) {

		if (rawClass.length == 0) {
			throw new ClassTransformationException("Tried to read empty byte array");
		}
		classReader = new ClassReader(rawClass);
		return this;
	}

	/**
	 * Visit class structure with {@link ClassVisitor} passed
	 * as constructor argument to this transformer instance.
	 *
	 * @return {@code this} instance of {@link StormClassTransformer}.
	 *
	 * @see <a href="https://asm.ow2.io/asm4-guide.pdf#section.2.2">
	 * 		ASM User Guide - Class interfaces and components</a>
	 */
	@Contract("-> this")
	StormClassTransformer visit() {

		if (classReader != null) {
			classReader.accept(visitor, 0);
		}
		else StormLogger.warn("Tried to visit null ClassReader");
		return this;
	}

	/**
	 * Write the class parsed by visitor using {@link ClassWriter} to
	 * convert the visited class to byte array. The resulting byte array
	 * can then be used to define the class by class loaders.
	 *
	 * @return byte array representing transformed class.
	 *
	 * @see <a href="https://asm.ow2.io/asm4-guide.pdf#subsection.6.2.1">
	 * 		ASM User Guide - Components composition</a>
	 */
	byte[] write() {

		ClassWriter classWriter = new ClassWriter(0);
		visitor.accept(classWriter);
		return classWriter.toByteArray();
	}

	/**
	 * Calls method chain to transform the given {@code Class} byte array.
	 *
	 * @param rawClass byte array representing the {@code Class} to transform.
	 *
	 * @return byte array representing transformed class.
	 * @throws ClassTransformationException if given byte array is empty.
	 */
	public byte[] transform(byte[] rawClass) {
		return read(rawClass).visit().transform().write();
	}

	/**
	 * Intermediary step between visiting and writing class.
	 * {@link StormClassTransformer} implementations need to override
	 * this method and are free to write their own transformation steps.
	 *
	 * @return {@code this} instance of {@link StormClassTransformer}.
	 */
	@Contract("-> this")
	abstract StormClassTransformer transform();
}
