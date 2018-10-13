package com.github.rafasantos.restapidoc;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.specification.FilterableRequestSpecification;

public class TextParser {
	
	// Utility classes should not have public constructors
	private TextParser() {}

	static String buildBody(FilterableRequestSpecification filterableRequestSpecification) {
		if (filterableRequestSpecification.getBody() == null) {
			return "\n";
		} else {
			String result = filterableRequestSpecification.getBody().toString();
			return JsonUtil.prettyJson(result);
		}
	}
	
	static String buildFormParms(Map<String, String> formParameters) {
		if (formParameters.isEmpty()) return "";
		StringBuilder result = new StringBuilder();
		if (!formParameters.isEmpty()) {
			result.append("-- Form Parameters --\n");
		}
		formParameters.forEach((key, value) -> {
			result.append("   "+ key + ": " + value + "\n");
		});
		return result.toString();
	}

	static String buildHeaders(final Headers headers) {
		StringBuilder result = new StringBuilder();
		List<Header> filteredHeaders = headers.asList().stream()
			.filter(header -> {
				return !("Accept".equalsIgnoreCase(header.getName()) && "*/*".equals(header.getValue()));
			})
			.collect(Collectors.toList());
		if (!filteredHeaders.isEmpty()) {
			result.append("-- Headers --\n");
		}
		filteredHeaders.forEach(h -> {
			result.append("   " + h.getName() + ": " + h.getValue() + "\n");
		});
		return result.toString();
	}
	
}
