package io.pzstorm.storm.core;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceMethodVisitor;

import com.github.difflib.DiffUtils;
import com.github.difflib.UnifiedDiffUtils;
import com.github.difflib.patch.Patch;

import io.pzstorm.storm.StormLogger;

/**
 * This class represents a {@code Class} transformer used to alter {@code Class}
 * bytecode using ASM when running JUnit tests. When transforming it will print a
 * detailed diff of changed bytecode using {@link StormLogger} to make debugging easier.
 */
public abstract class StormClassTestTransformer extends StormClassTransformer {

	private static final Printer ASM_PRINTER = new Textifier();
	private static final TraceMethodVisitor METHOD_PRINTER = new TraceMethodVisitor(ASM_PRINTER);

	StormClassTestTransformer(String className) {
		super(className);
	}

	/**
	 * Returns the text constructed from given node's bytecode.
	 *
	 * @param insnNode node to construct the text for.
	 */
	private static String getBytecodeFor(AbstractInsnNode insnNode) {
		insnNode.accept(METHOD_PRINTER);

		StringWriter stringWriter = new StringWriter();
		ASM_PRINTER.print(new PrintWriter(stringWriter));
		ASM_PRINTER.getText().clear();

		return stringWriter.toString();
	}

	/**
	 * Returns the text constructed from given {@link ClassNode} bytecode.
	 *
	 * @param classNode node to construct the text for.
	 */
	private static String getBytecodeFor(ClassNode classNode) {

		StringBuilder builder = new StringBuilder();
		for (MethodNode methodNode : classNode.methods)
		{
			builder.append(String.format("Method %s -> %s\n", methodNode.name, methodNode.desc));
			for (AbstractInsnNode insnNode : methodNode.instructions) {
				builder.append(String.format(".... %s", getBytecodeFor(insnNode)));
			}
		}
		return builder.toString();
	}

	/**
	 * Returns the Unified Diff format text that represents the bytecode difference
	 * between {@code originalCode} and {@code modifiedCode}.
	 *
	 * @param className filename of the original (unrevised file).
	 * @param originalCode lines of the original file.
	 * @param modifiedCode lines of the modified file.
	 * @return bytecode difference between two set of code lines.
	 */
	private static String getBytecodeDiff(String className, String originalCode, String modifiedCode) {

		List<String> originalLines = Arrays.asList(originalCode.split("\n"));
		List<String> modifiedLines = Arrays.asList(modifiedCode.split("\n"));

		Patch<String> bytecodePatch = DiffUtils.diff(originalLines, modifiedLines);
		List<String> unifiedDiff = UnifiedDiffUtils.generateUnifiedDiff(
				className + ".class (original)",
				className + ".class (transformed)",
				originalLines, bytecodePatch, 4
		);
		StringBuilder builder = new StringBuilder();
		for (String diffLine : unifiedDiff) {
			builder.append(diffLine).append("\n");
		}
		return builder.toString();
	}

	@Override
	public byte[] transform(byte[] rawClass) {

		read(rawClass).visit();
		String original = getBytecodeFor(visitor);

		byte[] result = transform().write();
		String modifier = getBytecodeFor(visitor);

		StormLogger.info("Generating class bytecode diff");
		String diff = getBytecodeDiff(className, original, modifier);
		if (!diff.isEmpty()) {
			//noinspection UseOfSystemOutOrSystemErr
			System.out.println(diff);
		}
		else StormLogger.warn("No diff available, class bytecode is identical");
		return result;
	}
}
