package org.vieuxchameau.bcsgtest.service;

import org.junit.Test;
import org.vieuxchameau.bcsgtest.domain.CreditCard;

import static org.assertj.core.api.StrictAssertions.assertThat;
import static org.vieuxchameau.bcsgtest.service.MaskService.maskCardNumber;

public class MaskServiceTest {
	@Test
	public void should_mask_card_number_for_hsbc() {
		final CreditCard hsbcCreditCard = new CreditCard("HSBC Canada", "5601-2345-3446-5678", "Nov-2017");

		final String maskCardNumber = maskCardNumber(hsbcCreditCard);
		assertThat(maskCardNumber).isEqualTo("56xx-xxxx-xxxx-xxxx");
	}

	@Test
	public void should_mask_card_number_for_royal_bank_ca() {
		final CreditCard rbocCreditCard = new CreditCard("Royal Bank of  Canada", "4519-4532-4524-2456", "Oct-2017");

		final String maskCardNumber = maskCardNumber(rbocCreditCard);

		assertThat(maskCardNumber).isEqualTo("4519-xxxx-xxxx-xxxx");
	}

	@Test
	public void should_mask_card_number_for_american_express() {
		final CreditCard amexCreditCard = new CreditCard("American Express", "3786-7334-8965-345", "Dec-2018");

		final String maskCardNumber = maskCardNumber(amexCreditCard);

		assertThat(maskCardNumber).isEqualTo("xxxx-xxxx-xxxx-345");
	}

	@Test
	public void should_mask_all_card_numbers_digits_for_unknown_bank() {
		final CreditCard unknownCreditCard = new CreditCard("Unknown bank", "3786-7334-8965-345", "Dec-2018");

		final String maskCardNumber = maskCardNumber(unknownCreditCard);

		assertThat(maskCardNumber).isEqualTo("xxxx-xxxx-xxxx-xxx");
	}
}