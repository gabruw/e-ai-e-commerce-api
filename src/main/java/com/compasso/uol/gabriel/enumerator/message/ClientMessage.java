package com.compasso.uol.gabriel.enumerator.message;

public enum ClientMessage {
	ALREADYEXISTSCPF("alreadyexists.cpf");

	private final String text;

	ClientMessage(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
