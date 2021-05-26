package io.pzstorm.storm;

import java.io.File;

import org.junit.jupiter.api.Tag;

@Tag("integration")
public interface IntegrationTest {

	static File getTemporaryBuildDir(Class<?> testClass) {
		return new File("build/tmp/integrationTest/" + testClass.getSimpleName());
	}
}
