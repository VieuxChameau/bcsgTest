package org.vieuxchameau.bcsgtest.service;

import org.vieuxchameau.bcsgtest.domain.CreditCard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CsvService {
	private static final String SEPARATOR = ",";
	private static final int BANK_NAME_INDEX = 0;
	private static final int CARD_NUMBER_INDEX = 1;
	private static final int EXPIRATION_DATE_NUMBER_INDEX = 2;

	private static final Function<String, String[]> lineToCardData = line -> line.split(SEPARATOR);
	private static final Function<String[], CreditCard> cardDataToToCreditCard = data -> new CreditCard(data[BANK_NAME_INDEX], data[CARD_NUMBER_INDEX], data[EXPIRATION_DATE_NUMBER_INDEX]);

	public List<CreditCard> parseFile(final String fileName) {
		try (InputStream is = new FileInputStream(new File(fileName))) {
			final BufferedReader br = new BufferedReader(new InputStreamReader(is));
			return getCreditCardsFromLines(br);
		} catch (Exception ex) {
			System.err.println("Error parsing the file " + ex);
			throw new IllegalArgumentException("Invalid file", ex);
		}
	}

	/**
	 * Convert each line to a credit card
	 */
	private List<CreditCard> getCreditCardsFromLines(final BufferedReader br) {
		return br.lines()
				// first split the CSV line and then convert the data to a Credit Card
				.map(lineToCardData.andThen(cardDataToToCreditCard))
				.collect(Collectors.toList());
	}
}
