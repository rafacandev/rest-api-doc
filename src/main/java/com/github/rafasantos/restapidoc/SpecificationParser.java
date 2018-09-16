package com.github.rafasantos.restapidoc;

import io.restassured.specification.FilterableRequestSpecification;

public class SpecificationParser {

	private SpecificationFilter filter;
	
	public SpecificationParser(SpecificationFilter specificationFilter) {
		this.filter = specificationFilter;
	}
	
	private FilterableRequestSpecification getRequest() {
		return filter.getFilterableRequestSpecification();
	}

	public String requestAsCurl() {
		StringBuilder result = new StringBuilder();
		result.append(CurlParser.buildMethod(getRequest().getMethod()));
		result.append(CurlParser.buildHeaders(getRequest().getHeaders()));
		if (getRequest().getBody() != null) {
			result.append(CurlParser.buildBody(getRequest()));
		} else {
			result.append(CurlParser.buildFormParms(getRequest().getFormParams()));
		}
		result.append(CurlParser.escapeUrl(getRequest().getURI()));
		return result.toString();
	}
	
}
