package org.vieuxchameau.bcsgtest;

import org.junit.Test;

import java.net.URL;

public class LauncherTest {
	@Test(expected = IllegalArgumentException.class)
	public void should_fail_if_fileName_is_Missing() {
		Launcher.main(new String[0]);
	}

	@Test
	public void should_display_cards() {
		final String validFile = getAbsolutePath("/mid-test.csv");

		Launcher.main(new String[]{validFile});
	}

	private String getAbsolutePath(final String fileName) {
		final URL url = this.getClass().getResource(fileName);
		return url.getFile();
	}
}