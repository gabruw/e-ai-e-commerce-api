package com.compasso.uol.gabriel.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Crypt {
	private static final Logger log = LoggerFactory.getLogger(Crypt.class);

	public static String encode(String value) {
		log.info("Encriptando valor: {}", value);

		return new BCryptPasswordEncoder().encode(value);
	}

	public static Boolean matches(String encoded, String value) {
		log.info("Comparando valores: {}", value);

		return new BCryptPasswordEncoder().matches(value, encoded);
	}
}
