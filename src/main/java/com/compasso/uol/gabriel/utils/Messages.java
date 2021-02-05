package com.compasso.uol.gabriel.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import com.compasso.uol.gabriel.response.ResponseError;

public class Messages {
	public static ResponseError getAuthentication(String key) {
		ResourceBundle bundle = ResourceBundle.getBundle("messages.AuthenticationMessages", Locale.getDefault());
		return getMessages(bundle, key);
	}

	public static ResponseError getAddress(String key) {
		ResourceBundle bundle = ResourceBundle.getBundle("messages.AddressMessages", Locale.getDefault());
		return getMessages(bundle, key);
	}

	public static ResponseError getClient(String key) {
		ResourceBundle bundle = ResourceBundle.getBundle("messages.ClientMessages", Locale.getDefault());
		return getMessages(bundle, key);
	}

	public static ResponseError getCity(String key) {
		ResourceBundle bundle = ResourceBundle.getBundle("messages.CityMessages", Locale.getDefault());
		return getMessages(bundle, key);
	}

	public static ResponseError getState(String key) {
		ResourceBundle bundle = ResourceBundle.getBundle("messages.StateMessages", Locale.getDefault());
		return getMessages(bundle, key);
	}

	private static ResponseError getMessages(ResourceBundle bundle, String key) {
		List<String> keys = convert(key);

		String text = bundle.getString(keys.get(0));
		String title = bundle.getString(keys.get(1));
		return ResponseError.convert(title, text);
	}

	private static List<String> convert(String key) {
		String text = key.concat(".text");
		String title = key.concat(".title");

		List<String> converted = new ArrayList<String>();
		converted.add(title);
		converted.add(text);

		return converted;
	}
}
