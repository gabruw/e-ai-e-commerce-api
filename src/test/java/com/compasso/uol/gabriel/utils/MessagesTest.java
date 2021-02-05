package com.compasso.uol.gabriel.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.compasso.uol.gabriel.enumerator.message.AuthenticationMessage;
import com.compasso.uol.gabriel.enumerator.message.CityMessage;
import com.compasso.uol.gabriel.enumerator.message.ClientMessage;
import com.compasso.uol.gabriel.enumerator.message.CommomMessage;
import com.compasso.uol.gabriel.enumerator.message.StateMessage;
import com.compasso.uol.gabriel.response.ResponseError;

public class MessagesTest {

	@Test
	public void getValidCityMessage() {
		ResponseError error = Messages.getCity(CityMessage.ALREADYEXISTSNAME.toString());

		Assertions.assertTrue(error.getTitle() != null && error.getText() != null);
	}

	@Test
	public void getValidStateMessage() {
		ResponseError error = Messages.getCity(StateMessage.ALREADYEXISTSNAME.toString());

		Assertions.assertTrue(error.getTitle() != null && error.getText() != null);
	}

	@Test
	public void getValidAddressMessage() {
		ResponseError error = Messages.getCity(CommomMessage.NONEXISTENT.toString());

		Assertions.assertTrue(error.getTitle() != null && error.getText() != null);
	}

	@Test
	public void getValidClientMessage() {
		ResponseError error = Messages.getClient(ClientMessage.ALREADYEXISTSCPF.toString());

		Assertions.assertTrue(error.getTitle() != null && error.getText() != null);
	}

	@Test
	public void getValidCAuthenticationMessage() {
		ResponseError error = Messages.getAuthentication(AuthenticationMessage.ALREADYEXISTSEMAIL.toString());

		Assertions.assertTrue(error.getTitle() != null && error.getText() != null);
	}
}
