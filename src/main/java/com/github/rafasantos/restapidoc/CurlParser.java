package com.github.rafasantos.restapidoc;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringEscapeUtils;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.specification.FilterableRequestSpecification;

public class CurlParser {
	
	private static final String NEW_LINE = " \\\n";
	
	// Utility classes should not have public constructors
	private CurlParser() {}

	static String buildBody(FilterableRequestSpecification filterableRequestSpecification) {
		if (filterableRequestSpecification.getBody() == null) return "";
		StringBuilder result = new StringBuilder();
		String requestBody = filterableRequestSpecification.getBody().toString().replace("\n", "");
		// At first glance, escaping as JavaScript looks very close to escaping as bash, so let's use it for now.
		requestBody = StringEscapeUtils.escapeJavaScript(requestBody);
		result.append("-d \"" + requestBody + "\"");
		result.append(NEW_LINE);
		return result.toString();
	}
	
	static String buildFormParms(Map<String, String> formParameters) {
		if (formParameters.isEmpty()) return "";
		StringBuilder result = new StringBuilder();
		formParameters.forEach((key, value) -> {
			result.append("-F ");
			result.append("\"" + key + "=" + value + "\"");
			result.append(NEW_LINE);
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
		
		filteredHeaders.forEach(h -> {
			result.append("-H \"" + h.getName() + ": ");
			result.append(h.getValue() + "\" \\\n");
		});
		return result.toString();
	}
	
	static String buildMethod(final String method) {
		StringBuilder result = new StringBuilder("curl -v");
		if (!"GET".equalsIgnoreCase(method)) {
			result.append(" -X " + method);
		}
		result.append(NEW_LINE);
		return result.toString();
	}

	/*
	 * Is necessary to escape '&' with '\&' when running curl
	 * 
	 * Example: 
	 * <pre>
	 * 	This URL is invalid in curl: http://localhost:8081?_pageSize=10&_pageNumber=1
	 * 	This URL is valid in curl: http://localhost:8081?_pageSize=10\&_pageNumber=1
	 * </pre>
	 * 
	 * @param url
	 * @return escapedURL
	 */
	public static String escapeUrl(String url) {
		return url.replace("&", "\\&");
	}

}
