package com.compasso.uol.gabriel.enumerator.message;

public enum StateMessage {
	ALREADYEXISTSNAME("alreadyexists.name");

	private final String text;

	StateMessage(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
