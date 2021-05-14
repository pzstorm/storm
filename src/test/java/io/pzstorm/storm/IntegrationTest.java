package io.pzstorm.storm;

import org.junit.jupiter.api.Tag;

import java.io.File;

@Tag("integration")
public interface IntegrationTest {

	static File getTemporaryBuildDir(Class<?> testClass) {
		return new File("build/tmp/integrationTest/" + testClass.getSimpleName());
	}
}
