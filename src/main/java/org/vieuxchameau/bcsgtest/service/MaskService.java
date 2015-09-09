package org.vieuxchameau.bcsgtest.service;

import org.vieuxchameau.bcsgtest.domain.CreditCard;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class MaskService {
	private static final List<Strategy> STRATEGIES = new ArrayList<>();

	private static final Predicate<String> alwaysTrue = card -> true;

	static {
		STRATEGIES.add(new Strategy("HSBC Canada"::equalsIgnoreCase, replaceFrom(2)));
		STRATEGIES.add(new Strategy("Royal Bank of  Canada"::equalsIgnoreCase, replaceFrom(4)));
		STRATEGIES.add(new Strategy("American Express"::equalsIgnoreCase, replaceUntilLast()));
		STRATEGIES.add(new Strategy(alwaysTrue, (cardNumber) -> cardNumber.replaceAll("\\d", "x")));
	}

	/**
	 * Mask and return the credit card number depending on the bank.
	 * If the bank is unknown, hide all digits.
	 */
	public static String maskCardNumber(final CreditCard creditCard) {
		for (final Strategy strategy : STRATEGIES) {
			if (strategy.match(creditCard.getBankName())) {
				return strategy.mask(creditCard.getCardNumber());
			}
		}
		throw new IllegalStateException("A strategy should match");
	}

	private static Function<String, String> replaceFrom(int index) {
		return number -> number.substring(0, index) + number.substring(index).replaceAll("(\\d)", "x");
	}

	private static Function<String, String> replaceUntilLast() {
		return number -> number.substring(0, number.lastIndexOf('-')).replaceAll("(\\d)", "x") + number.substring(number.lastIndexOf('-'));
	}

	private static class Strategy {
		private final Predicate<String> bankPattern;
		private final Function<String, String> maskFunction;

		private Strategy(final Predicate<String> bankPattern, final Function<String, String> maskFunction) {
			this.bankPattern = bankPattern;
			this.maskFunction = maskFunction;
		}

		boolean match(final String bankName) {
			return bankPattern.test(bankName);
		}

		String mask(final String cardNumber) {
			return maskFunction.apply(cardNumber);
		}
	}
}
