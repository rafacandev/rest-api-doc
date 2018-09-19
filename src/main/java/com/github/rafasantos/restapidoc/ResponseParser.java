package com.github.rafasantos.restapidoc;

import io.restassured.http.Headers;

public class ResponseParser {

	public static String buildHeaders(Headers headers) {
		StringBuilder result = new StringBuilder();
		headers.forEach(header -> {
			result.append(header.getName() + ": " + header.getValue() + "\n");
		});
		return result.toString();
	}

}
