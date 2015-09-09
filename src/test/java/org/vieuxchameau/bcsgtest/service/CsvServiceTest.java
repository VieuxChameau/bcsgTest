package org.vieuxchameau.bcsgtest.service;

import org.junit.Test;

import java.net.URL;

public class CsvServiceTest {
	private final CsvService service = new CsvService();

	@Test(expected = IllegalArgumentException.class)
	public void should_reject_non_existing_file() {
		final String nonExistingFileName = "badFile";

		service.parseFile(nonExistingFileName);
	}

	@Test
	public void should_parse_a_valid_file() {
		final String validFile = getAbsolutePath("/mid-test.csv");

		service.parseFile(validFile);
	}

	@Test
	public void should_not_fail_on_empty_file() {
		final String validFile = getAbsolutePath("/emptyFile.csv");

		service.parseFile(validFile);
	}

	private String getAbsolutePath(final String fileName) {
		final URL url = this.getClass().getResource(fileName);
		return url.getFile();
	}
}