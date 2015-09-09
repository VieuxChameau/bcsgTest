package org.vieuxchameau.bcsgtest.domain;

import org.junit.Test;

import java.time.YearMonth;
import java.time.format.DateTimeParseException;

import static java.time.Month.DECEMBER;
import static org.assertj.core.api.StrictAssertions.assertThat;

public class CreditCardTest {
	@Test(expected = IllegalArgumentException.class)
	public void should_reject_null_bank_name() {
		final String bankName = null;
		final String creditCardNumber = "3786-7334-8965-345";
		final String expirationDate = "Dec-2018";

		new CreditCard(bankName, creditCardNumber, expirationDate);
	}

	@Test(expected = IllegalArgumentException.class)
	public void should_reject_empty_bank_name() {
		final String bankName = "";
		final String creditCardNumber = "3786-7334-8965-345";
		final String expirationDate = "Dec-2018";

		new CreditCard(bankName, creditCardNumber, expirationDate);
	}

	@Test(expected = IllegalArgumentException.class)
	public void should_reject_null_card_number() {
		final String bankName = "American Express";
		final String creditCardNumber = null;
		final String expirationDate = "Dec-2018";

		new CreditCard(bankName, creditCardNumber, expirationDate);
	}

	@Test(expected = IllegalArgumentException.class)
	public void should_reject_empty_card_number() {
		final String bankName = "American Express";
		final String creditCardNumber = "";
		final String expirationDate = "Dec-2018";

		new CreditCard(bankName, creditCardNumber, expirationDate);
	}

	@Test(expected = IllegalArgumentException.class)
	public void should_reject_null_expiration_date() {
		final String bankName = "American Express";
		final String creditCardNumber = "3786-7334-8965-345";
		final String expirationDate = null;

		new CreditCard(bankName, creditCardNumber, expirationDate);
	}

	@Test(expected = IllegalArgumentException.class)
	public void should_reject_empty_expiration_date() {
		final String bankName = "American Express";
		final String creditCardNumber = "3786-7334-8965-345";
		final String expirationDate = "";

		new CreditCard(bankName, creditCardNumber, expirationDate);
	}

	@Test(expected = DateTimeParseException.class)
	public void should_reject_invalid_format_for_expiration_date() {
		final String bankName = "American Express";
		final String creditCardNumber = "3786-7334-8965-345";
		final String expirationDate = "12-2018";

		new CreditCard(bankName, creditCardNumber, expirationDate);
	}

	@Test
	public void should_create_valid_card() {
		final String bankName = "American Express";
		final String creditCardNumber = "3786-7334-8965-345";
		final String expirationDate = "Dec-2018";

		final CreditCard creditCard = new CreditCard(bankName, creditCardNumber, expirationDate);

		assertThat(creditCard.getBankName()).isEqualTo("American Express");
		assertThat(creditCard.getCardNumber()).isEqualTo("3786-7334-8965-345");
		final YearMonth expectedExpirationDate = YearMonth.of(2018, DECEMBER);
		assertThat(creditCard.getExpirationDate()).isEqualTo(expectedExpirationDate);
	}

	@Test
	public void should_trim_values_for_valid_card() {
		final String bankName = " 	American Express 	";
		final String creditCardNumber = " 	3786-7334-8965-345		 ";
		final String expirationDate = "		Dec-2018 	";

		final CreditCard creditCard = new CreditCard(bankName, creditCardNumber, expirationDate);

		assertThat(creditCard.getBankName()).isEqualTo("American Express");
		assertThat(creditCard.getCardNumber()).isEqualTo("3786-7334-8965-345");
		final YearMonth expectedExpirationDate = YearMonth.of(2018, DECEMBER);
		assertThat(creditCard.getExpirationDate()).isEqualTo(expectedExpirationDate);
	}

	@Test
	public void toString_should_not_contains_card_number() {
		final String bankName = "American Express";
		final String creditCardNumber = "3786-7334-8965-345";
		final String expirationDate = "Dec-2018";

		final CreditCard creditCard = new CreditCard(bankName, creditCardNumber, expirationDate);

		assertThat(creditCard.toString()).doesNotContain(creditCardNumber);
	}
}