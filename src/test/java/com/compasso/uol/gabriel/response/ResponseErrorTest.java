package com.compasso.uol.gabriel.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResponseErrorTest {

	private static final String TEXT = "Teste Text";
	private static final String TITLE = "Teste Title";

	@Test
	public void buildWithConstructor() {
		ResponseError error = new ResponseError(TEXT, TITLE);

		Assertions.assertTrue(error.getTitle().equals(TITLE) && error.getText().equals(TEXT));
	}

	@Test
	public void buildWithSetters() {
		ResponseError error = new ResponseError();
		error.setText(TEXT);
		error.setTitle(TITLE);

		Assertions.assertTrue(error.getTitle().equals(TITLE) && error.getText().equals(TEXT));
	}

	@Test
	public void buildWithConvert() {
		ResponseError error = ResponseError.convert(TEXT, TITLE);

		Assertions.assertTrue(error.getTitle().equals(TITLE) && error.getText().equals(TEXT));
	}
}
