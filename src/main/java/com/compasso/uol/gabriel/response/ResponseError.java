package com.compasso.uol.gabriel.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseError {
	private String text;
	private String title;

	public static ResponseError convert(String title, String text) {
		return new ResponseError(title, text);
	}
}
