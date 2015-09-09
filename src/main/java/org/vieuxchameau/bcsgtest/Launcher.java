package org.vieuxchameau.bcsgtest;

import org.vieuxchameau.bcsgtest.domain.CreditCard;
import org.vieuxchameau.bcsgtest.service.CsvService;

import java.util.List;

import static java.util.Comparator.comparing;

public class Launcher {
	public static void main(final String[] args) {
		if (args.length != 1) {
			throw new IllegalArgumentException("Invalid argument, expected absolute path to a csv");
		}

		final CsvService service = new CsvService();
		final List<CreditCard> creditCards = service.parseFile(args[0]);

		creditCards.stream() //
				.sorted(comparing(CreditCard::getExpirationDate).reversed()) //
				.forEach(System.out::println);
	}
}
