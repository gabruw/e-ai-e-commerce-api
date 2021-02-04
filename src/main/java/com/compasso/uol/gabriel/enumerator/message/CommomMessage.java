package com.compasso.uol.gabriel.enumerator.message;

public enum CommomMessage {
	NONEXISTENT("nonexistent");

	private final String text;

	CommomMessage(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
