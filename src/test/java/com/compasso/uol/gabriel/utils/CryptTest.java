package com.compasso.uol.gabriel.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CryptTest {

	private static final String PASSWORD = "teste@123";

	@Test
	public void convertAndMatchValidValue() {
		String encoded = Crypt.encode(PASSWORD);
		Boolean matched = Crypt.matches(encoded, PASSWORD);

		Assertions.assertTrue(matched);
	}

	@Test
	public void convertAndMatchInvalidValue() {
		String encoded = Crypt.encode("isn't value here :D");
		Boolean matched = Crypt.matches(encoded, PASSWORD);

		Assertions.assertFalse(matched);
	}
}
