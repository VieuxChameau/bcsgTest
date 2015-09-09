package org.vieuxchameau.bcsgtest.domain;

import org.vieuxchameau.bcsgtest.service.MaskService;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CreditCard {
	private static final DateTimeFormatter EXPIRATION_DATE_PATTERN = DateTimeFormatter.ofPattern("MMM-yyyy", Locale.ENGLISH);

	private final String bankName;
	private final String cardNumber;
	private final YearMonth expirationDate;

	public CreditCard(final String bankName, final String cardNumber, final String expirationDate) {
		this.bankName = checkNotEmptyAfterTrim(bankName, "Bank Name can not be null or empty");
		this.cardNumber = checkNotEmptyAfterTrim(cardNumber, "Card Number can not be null or empty");
		final String trimmedDate = checkNotEmptyAfterTrim(expirationDate, "Expiration Date can not be null or empty");
		this.expirationDate = YearMonth.parse(trimmedDate, EXPIRATION_DATE_PATTERN);
	}

	public String getBankName() {
		return bankName;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public YearMonth getExpirationDate() {
		return expirationDate;
	}

	private String checkNotEmptyAfterTrim(final String value, final String message) {
		if (value == null) {
			throw new IllegalArgumentException(message);
		}

		final String trimmedValue = value.trim();
		if (value.isEmpty()) {
			throw new IllegalArgumentException(message);
		}
		return trimmedValue;
	}

	@Override
	public String toString() {
		return String.format("%s | %s | %s", bankName, MaskService.maskCardNumber(this), expirationDate.format(EXPIRATION_DATE_PATTERN));
	}
}
